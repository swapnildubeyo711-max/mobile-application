from flask import Flask, render_template, request, jsonify, send_from_directory
from flask_cors import CORS
import sqlite3
import json
from datetime import datetime, timedelta
import os

app = Flask(__name__)
CORS(app)

# Database connection helper
def get_db_connection():
    conn = sqlite3.connect('database/hotel_app.db')
    conn.row_factory = sqlite3.Row
    return conn

# Initialize database on startup
def init_db():
    from database_setup import create_database
    create_database()

@app.route('/')
def index():
    """Main page"""
    return render_template('index.html')

@app.route('/api/states')
def get_states():
    """Get all states with their pricing information"""
    conn = get_db_connection()
    states = conn.execute('''
        SELECT s.*, COUNT(h.id) as hotel_count
        FROM states s
        LEFT JOIN hotels h ON s.id = h.state_id
        GROUP BY s.id
        ORDER BY s.name
    ''').fetchall()
    conn.close()
    
    return jsonify([dict(state) for state in states])

@app.route('/api/hotels')
def get_hotels():
    """Get hotels, optionally filtered by state"""
    state_id = request.args.get('state_id')
    
    conn = get_db_connection()
    
    if state_id:
        hotels = conn.execute('''
            SELECT h.*, s.name as state_name, s.price_multiplier
            FROM hotels h
            JOIN states s ON h.state_id = s.id
            WHERE h.state_id = ?
            ORDER BY h.rating DESC
        ''', (state_id,)).fetchall()
    else:
        hotels = conn.execute('''
            SELECT h.*, s.name as state_name, s.price_multiplier
            FROM hotels h
            JOIN states s ON h.state_id = s.id
            ORDER BY h.rating DESC
        ''').fetchall()
    
    conn.close()
    
    return jsonify([dict(hotel) for hotel in hotels])

@app.route('/api/hotels/<int:hotel_id>')
def get_hotel_details(hotel_id):
    """Get detailed information about a specific hotel"""
    conn = get_db_connection()
    
    # Get hotel info
    hotel = conn.execute('''
        SELECT h.*, s.name as state_name, s.price_multiplier, s.monument_image, s.description as state_description
        FROM hotels h
        JOIN states s ON h.state_id = s.id
        WHERE h.id = ?
    ''', (hotel_id,)).fetchone()
    
    if not hotel:
        conn.close()
        return jsonify({'error': 'Hotel not found'}), 404
    
    # Get room types
    room_types = conn.execute('''
        SELECT * FROM room_types
        WHERE hotel_id = ?
        ORDER BY base_price
    ''', (hotel_id,)).fetchall()
    
    conn.close()
    
    hotel_dict = dict(hotel)
    hotel_dict['room_types'] = [dict(room) for room in room_types]
    
    return jsonify(hotel_dict)

@app.route('/api/calculate-price')
def calculate_price():
    """Calculate total price based on hotel, room type, dates, and state pricing"""
    hotel_id = request.args.get('hotel_id')
    room_type_id = request.args.get('room_type_id')
    check_in = request.args.get('check_in')
    check_out = request.args.get('check_out')
    
    if not all([hotel_id, room_type_id, check_in, check_out]):
        return jsonify({'error': 'Missing required parameters'}), 400
    
    try:
        check_in_date = datetime.strptime(check_in, '%Y-%m-%d')
        check_out_date = datetime.strptime(check_out, '%Y-%m-%d')
        nights = (check_out_date - check_in_date).days
        
        if nights <= 0:
            return jsonify({'error': 'Invalid date range'}), 400
        
        conn = get_db_connection()
        
        # Get room and state pricing info
        result = conn.execute('''
            SELECT rt.base_price, s.price_multiplier
            FROM room_types rt
            JOIN hotels h ON rt.hotel_id = h.id
            JOIN states s ON h.state_id = s.id
            WHERE rt.id = ? AND h.id = ?
        ''', (room_type_id, hotel_id)).fetchone()
        
        conn.close()
        
        if not result:
            return jsonify({'error': 'Room type not found'}), 404
        
        base_price = result['base_price']
        multiplier = result['price_multiplier']
        
        # Calculate total price with state multiplier
        nightly_rate = base_price * multiplier
        total_price = nightly_rate * nights
        
        return jsonify({
            'base_price': base_price,
            'state_multiplier': multiplier,
            'nightly_rate': round(nightly_rate, 2),
            'nights': nights,
            'total_price': round(total_price, 2)
        })
        
    except ValueError:
        return jsonify({'error': 'Invalid date format'}), 400

@app.route('/api/book', methods=['POST'])
def create_booking():
    """Create a new booking"""
    data = request.json
    
    required_fields = ['hotel_id', 'room_type_id', 'guest_name', 'guest_email', 
                      'check_in_date', 'check_out_date', 'num_guests']
    
    if not all(field in data for field in required_fields):
        return jsonify({'error': 'Missing required fields'}), 400
    
    try:
        conn = get_db_connection()
        
        # Calculate total price directly
        check_in_date = datetime.strptime(data['check_in_date'], '%Y-%m-%d')
        check_out_date = datetime.strptime(data['check_out_date'], '%Y-%m-%d')
        nights = (check_out_date - check_in_date).days
        
        if nights <= 0:
            conn.close()
            return jsonify({'error': 'Invalid date range'}), 400
        
        # Get room and state pricing info
        result = conn.execute('''
            SELECT rt.base_price, s.price_multiplier
            FROM room_types rt
            JOIN hotels h ON rt.hotel_id = h.id
            JOIN states s ON h.state_id = s.id
            WHERE rt.id = ? AND h.id = ?
        ''', (data['room_type_id'], data['hotel_id'])).fetchone()
        
        if not result:
            conn.close()
            return jsonify({'error': 'Room type not found'}), 404
        
        base_price = result['base_price']
        multiplier = result['price_multiplier']
        nightly_rate = base_price * multiplier
        total_price = nightly_rate * nights
        
        # Check room availability
        available_rooms = conn.execute('''
            SELECT available_rooms FROM room_types
            WHERE id = ?
        ''', (data['room_type_id'],)).fetchone()
        
        if not available_rooms or available_rooms['available_rooms'] <= 0:
            conn.close()
            return jsonify({'error': 'No rooms available'}), 400
        
        # Create booking
        cursor = conn.cursor()
        cursor.execute('''
            INSERT INTO bookings (hotel_id, room_type_id, guest_name, guest_email, guest_phone,
                                check_in_date, check_out_date, num_guests, total_price)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        ''', (data['hotel_id'], data['room_type_id'], data['guest_name'], data['guest_email'],
              data.get('guest_phone', ''), data['check_in_date'], data['check_out_date'],
              data['num_guests'], total_price))
        
        booking_id = cursor.lastrowid
        
        # Update available rooms
        cursor.execute('''
            UPDATE room_types 
            SET available_rooms = available_rooms - 1
            WHERE id = ?
        ''', (data['room_type_id'],))
        
        conn.commit()
        conn.close()
        
        return jsonify({
            'booking_id': booking_id,
            'message': 'Booking created successfully',
            'total_price': total_price
        })
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/api/bookings/<int:booking_id>')
def get_booking(booking_id):
    """Get booking details"""
    conn = get_db_connection()
    
    booking = conn.execute('''
        SELECT b.*, h.name as hotel_name, rt.type_name, s.name as state_name
        FROM bookings b
        JOIN hotels h ON b.hotel_id = h.id
        JOIN room_types rt ON b.room_type_id = rt.id
        JOIN states s ON h.state_id = s.id
        WHERE b.id = ?
    ''', (booking_id,)).fetchone()
    
    conn.close()
    
    if not booking:
        return jsonify({'error': 'Booking not found'}), 404
    
    return jsonify(dict(booking))

@app.route('/static/<path:filename>')
def serve_static(filename):
    """Serve static files"""
    return send_from_directory('static', filename)

if __name__ == '__main__':
    # Initialize database
    init_db()
    
    # Run the app
    app.run(debug=True, host='0.0.0.0', port=5000)