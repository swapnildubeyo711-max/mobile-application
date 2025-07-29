# Luxe Hotels - Premium Hotel Booking Application

A comprehensive hotel booking application built with Python Flask and SQL, featuring state-based pricing, professional design, and monument-themed destinations across America.

## 🏨 Features

### Core Functionality
- **Hotel Search & Booking**: Browse and book hotels across different states
- **State-Based Pricing**: Dynamic pricing system with different multipliers for each state
- **Real-Time Price Calculation**: Instant price updates based on dates, room type, and state
- **Room Management**: Multiple room types with availability tracking
- **Professional UI**: Modern, responsive design with professional hotel branding

### State-Specific Features
- **8 Featured States**: California, New York, Florida, Texas, Nevada, Arizona, Colorado, Washington
- **Monument Themes**: Each state features iconic monuments and landmarks
- **Dynamic Pricing**: Price multipliers ranging from 0.9x (Arizona) to 1.8x (New York)
- **State Icons**: Visual representation with relevant emojis for each destination

### Technical Features
- **RESTful API**: Complete backend API with proper error handling
- **SQLite Database**: Normalized database schema with foreign key relationships
- **Responsive Design**: Mobile-first approach with modern CSS Grid and Flexbox
- **Professional Logo**: Custom CSS-based hotel logo with gradient effects
- **Modal System**: Interactive booking interface with price breakdown

## 🚀 Quick Start

### Prerequisites
- Python 3.7+
- pip (Python package installer)

### Installation

1. **Clone or navigate to the project directory:**
   ```bash
   cd hotel_app
   ```

2. **Install dependencies:**
   ```bash
   pip install -r requirements.txt
   ```

3. **Initialize the database:**
   ```bash
   python database_setup.py
   ```

4. **Run the application:**
   ```bash
   python app.py
   ```

5. **Open your browser and visit:**
   ```
   http://localhost:5000
   ```

## 📊 Database Schema

### States Table
- **id**: Primary key
- **name**: State name (e.g., "California")
- **code**: State code (e.g., "CA")
- **price_multiplier**: Pricing multiplier (e.g., 1.5 for California)
- **monument_image**: Associated monument image
- **description**: State description

### Hotels Table
- **id**: Primary key
- **name**: Hotel name
- **state_id**: Foreign key to states table
- **base_price**: Base room price before state multiplier
- **rating**: Hotel rating (1-5 stars)
- **amenities**: Comma-separated list of amenities
- **description**: Hotel description
- **address, phone, email**: Contact information

### Room Types Table
- **id**: Primary key
- **hotel_id**: Foreign key to hotels table
- **type_name**: Room type (e.g., "Standard Room", "Deluxe Suite")
- **base_price**: Base price for this room type
- **capacity**: Maximum number of guests
- **amenities**: Room-specific amenities
- **available_rooms**: Current availability

### Bookings Table
- **id**: Primary key
- **hotel_id, room_type_id**: Foreign keys
- **guest_name, guest_email, guest_phone**: Guest information
- **check_in_date, check_out_date**: Stay dates
- **num_guests**: Number of guests
- **total_price**: Final calculated price
- **booking_date**: When booking was made
- **status**: Booking status

## 🎨 Design Features

### Professional Hotel Logo
- Custom CSS-based logo design
- Gradient color scheme using brand colors
- Building icon representation
- Responsive scaling

### Color Scheme
- **Primary**: #2c3e50 (Dark blue-gray)
- **Secondary**: #e74c3c (Red)
- **Accent**: #f39c12 (Orange)
- **Background**: #ecf0f1 (Light gray)

### Monument Integration
Each state features iconic monuments and landmarks:
- 🌉 California: Golden Gate Bridge
- 🗽 New York: Statue of Liberty
- 🏖️ Florida: Art Deco Architecture
- 🤠 Texas: The Alamo
- 🎰 Nevada: Vegas Strip
- 🏜️ Arizona: Grand Canyon
- 🏔️ Colorado: Rocky Mountains
- 🌲 Washington: Space Needle

## 💰 Pricing System

### State-Based Multipliers
- **New York**: 1.8x (highest)
- **California**: 1.5x
- **Washington**: 1.4x
- **Nevada**: 1.3x
- **Florida**: 1.2x
- **Colorado**: 1.1x
- **Texas**: 1.0x (baseline)
- **Arizona**: 0.9x (lowest)

### Price Calculation
```
Final Price = Base Room Price × State Multiplier × Number of Nights
```

## 🔧 API Endpoints

### GET /api/states
Returns all states with hotel counts and pricing information.

### GET /api/hotels?state_id={id}
Returns hotels, optionally filtered by state.

### GET /api/hotels/{id}
Returns detailed hotel information including room types.

### GET /api/calculate-price
Calculates total price based on hotel, room type, and dates.
Parameters: `hotel_id`, `room_type_id`, `check_in`, `check_out`

### POST /api/book
Creates a new booking.
Body: Hotel details, guest information, and stay dates.

### GET /api/bookings/{id}
Returns booking details by booking ID.

## 📱 Responsive Design

- **Desktop**: Full grid layout with sidebar navigation
- **Tablet**: Responsive grid with stacked elements
- **Mobile**: Single-column layout with hamburger menu
- **Touch-Friendly**: Large buttons and touch targets

## 🛡️ Error Handling

- **Form Validation**: Client-side and server-side validation
- **Database Errors**: Graceful error handling with user-friendly messages
- **API Errors**: Proper HTTP status codes and error responses
- **User Feedback**: Success and error messages with auto-dismiss

## 📈 Sample Data

The application comes pre-loaded with:
- **8 States** with different pricing multipliers
- **6 Hotels** across various states
- **14 Room Types** with different amenities and pricing
- **Monument Themes** for each state

## 🔮 Future Enhancements

- **Image Upload**: Real monument and hotel images
- **Payment Integration**: Stripe or PayPal integration
- **User Accounts**: Registration and login system
- **Reviews**: Guest review and rating system
- **Search Filters**: Advanced filtering by amenities, price range
- **Email Notifications**: Booking confirmations and reminders
- **Admin Panel**: Hotel management interface

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support, please contact:
- **Email**: support@luxehotels.com
- **Phone**: 1-800-LUXE-HOTELS
- **Website**: https://luxehotels.com

---

**Luxe Hotels** - Experience luxury near iconic monuments with our state-based pricing system.