import sqlite3
import os

def create_database():
    """Create the SQLite database and tables for the hotel app"""
    
    # Ensure database directory exists
    os.makedirs('database', exist_ok=True)
    
    # Connect to SQLite database
    conn = sqlite3.connect('database/hotel_app.db')
    cursor = conn.cursor()
    
    # Create states table
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS states (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL UNIQUE,
            code TEXT NOT NULL UNIQUE,
            price_multiplier REAL NOT NULL DEFAULT 1.0,
            monument_image TEXT,
            description TEXT
        )
    ''')
    
    # Create hotels table
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS hotels (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            state_id INTEGER NOT NULL,
            base_price REAL NOT NULL,
            rating REAL DEFAULT 0.0,
            amenities TEXT,
            description TEXT,
            image_url TEXT,
            address TEXT,
            phone TEXT,
            email TEXT,
            FOREIGN KEY (state_id) REFERENCES states (id)
        )
    ''')
    
    # Create room_types table
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS room_types (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            hotel_id INTEGER NOT NULL,
            type_name TEXT NOT NULL,
            base_price REAL NOT NULL,
            capacity INTEGER NOT NULL,
            amenities TEXT,
            available_rooms INTEGER DEFAULT 0,
            FOREIGN KEY (hotel_id) REFERENCES hotels (id)
        )
    ''')
    
    # Create bookings table
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS bookings (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            hotel_id INTEGER NOT NULL,
            room_type_id INTEGER NOT NULL,
            guest_name TEXT NOT NULL,
            guest_email TEXT NOT NULL,
            guest_phone TEXT,
            check_in_date DATE NOT NULL,
            check_out_date DATE NOT NULL,
            num_guests INTEGER NOT NULL,
            total_price REAL NOT NULL,
            booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            status TEXT DEFAULT 'confirmed',
            FOREIGN KEY (hotel_id) REFERENCES hotels (id),
            FOREIGN KEY (room_type_id) REFERENCES room_types (id)
        )
    ''')
    
    # Insert sample states with different pricing
    states_data = [
        ('California', 'CA', 1.5, 'golden_gate.jpg', 'The Golden State with beautiful coastlines and iconic landmarks'),
        ('New York', 'NY', 1.8, 'statue_liberty.jpg', 'The Empire State with world-famous attractions'),
        ('Florida', 'FL', 1.2, 'art_deco.jpg', 'The Sunshine State with beautiful beaches and art deco architecture'),
        ('Texas', 'TX', 1.0, 'alamo.jpg', 'The Lone Star State with rich history and culture'),
        ('Nevada', 'NV', 1.3, 'vegas_strip.jpg', 'The Silver State home to Las Vegas entertainment'),
        ('Arizona', 'AZ', 0.9, 'grand_canyon.jpg', 'The Grand Canyon State with stunning natural wonders'),
        ('Colorado', 'CO', 1.1, 'rocky_mountains.jpg', 'The Centennial State with magnificent mountain ranges'),
        ('Washington', 'WA', 1.4, 'space_needle.jpg', 'The Evergreen State with modern cityscapes and nature')
    ]
    
    cursor.executemany('''
        INSERT OR IGNORE INTO states (name, code, price_multiplier, monument_image, description)
        VALUES (?, ?, ?, ?, ?)
    ''', states_data)
    
    # Insert sample hotels
    hotels_data = [
        ('Grand Pacific Hotel', 1, 250.00, 4.5, 'WiFi,Pool,Spa,Restaurant,Gym', 'Luxury hotel with ocean views', 'hotel1.jpg', '123 Ocean Ave, San Francisco, CA', '(415) 555-0123', 'info@grandpacific.com'),
        ('Empire State Lodge', 2, 300.00, 4.7, 'WiFi,Concierge,Restaurant,Bar,Gym', 'Elegant hotel in the heart of Manhattan', 'hotel2.jpg', '456 Broadway, New York, NY', '(212) 555-0456', 'reservations@empirelodge.com'),
        ('Sunshine Resort', 3, 180.00, 4.2, 'WiFi,Pool,Beach Access,Restaurant,Spa', 'Beachfront resort with tropical vibes', 'hotel3.jpg', '789 Beach Blvd, Miami, FL', '(305) 555-0789', 'bookings@sunshineresort.com'),
        ('Lone Star Inn', 4, 150.00, 4.0, 'WiFi,Pool,Restaurant,Parking', 'Comfortable stay with Texas hospitality', 'hotel4.jpg', '321 Main St, Austin, TX', '(512) 555-0321', 'stay@lonestarinn.com'),
        ('Desert Oasis Hotel', 6, 120.00, 4.3, 'WiFi,Pool,Spa,Restaurant,Casino', 'Modern hotel near Grand Canyon', 'hotel5.jpg', '654 Canyon Rd, Phoenix, AZ', '(602) 555-0654', 'info@desertoasis.com'),
        ('Mountain View Resort', 7, 200.00, 4.6, 'WiFi,Spa,Restaurant,Ski Access,Gym', 'Alpine resort with stunning mountain views', 'hotel6.jpg', '987 Mountain Pass, Denver, CO', '(303) 555-0987', 'reservations@mountainview.com')
    ]
    
    cursor.executemany('''
        INSERT OR IGNORE INTO hotels (name, state_id, base_price, rating, amenities, description, image_url, address, phone, email)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    ''', hotels_data)
    
    # Insert room types
    room_types_data = [
        (1, 'Standard Room', 250.00, 2, 'Queen bed, WiFi, TV, Mini-fridge', 10),
        (1, 'Deluxe Suite', 400.00, 4, 'King bed, Living area, Ocean view, Balcony', 5),
        (1, 'Presidential Suite', 800.00, 6, 'Master bedroom, Living room, Kitchen, Butler service', 2),
        (2, 'Standard Room', 300.00, 2, 'Queen bed, WiFi, TV, City view', 15),
        (2, 'Executive Suite', 500.00, 4, 'King bed, Work area, Manhattan view, Minibar', 8),
        (2, 'Penthouse', 1200.00, 8, 'Multiple bedrooms, Full kitchen, Terrace, Premium amenities', 1),
        (3, 'Beach Room', 180.00, 2, 'Queen bed, Beach view, WiFi, TV', 20),
        (3, 'Ocean Suite', 300.00, 4, 'King bed, Ocean view, Balcony, Jacuzzi', 10),
        (4, 'Standard Room', 150.00, 2, 'Queen bed, WiFi, TV, Parking', 25),
        (4, 'Family Suite', 250.00, 6, 'Two bedrooms, Living area, Kitchen', 8),
        (5, 'Desert Room', 120.00, 2, 'Queen bed, Desert view, WiFi, TV', 30),
        (5, 'Canyon Suite', 220.00, 4, 'King bed, Canyon view, Balcony, Spa access', 12),
        (6, 'Mountain Room', 200.00, 2, 'Queen bed, Mountain view, WiFi, Fireplace', 18),
        (6, 'Alpine Suite', 350.00, 4, 'King bed, Panoramic view, Hot tub, Ski storage', 6)
    ]
    
    cursor.executemany('''
        INSERT OR IGNORE INTO room_types (hotel_id, type_name, base_price, capacity, amenities, available_rooms)
        VALUES (?, ?, ?, ?, ?, ?)
    ''', room_types_data)
    
    conn.commit()
    conn.close()
    print("Database created successfully with sample data!")

if __name__ == "__main__":
    create_database()