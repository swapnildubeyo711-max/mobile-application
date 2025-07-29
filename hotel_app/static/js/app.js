// Hotel App JavaScript
class HotelApp {
    constructor() {
        this.currentHotel = null;
        this.states = [];
        this.hotels = [];
        this.init();
    }

    async init() {
        await this.loadStates();
        this.setupEventListeners();
        this.displayStates();
    }

    async loadStates() {
        try {
            const response = await fetch('/api/states');
            this.states = await response.json();
        } catch (error) {
            console.error('Error loading states:', error);
            this.showError('Failed to load states');
        }
    }

    async loadHotels(stateId = null) {
        try {
            const url = stateId ? `/api/hotels?state_id=${stateId}` : '/api/hotels';
            const response = await fetch(url);
            this.hotels = await response.json();
            this.displayHotels();
        } catch (error) {
            console.error('Error loading hotels:', error);
            this.showError('Failed to load hotels');
        }
    }

    setupEventListeners() {
        // Search form
        const searchForm = document.getElementById('searchForm');
        if (searchForm) {
            searchForm.addEventListener('submit', (e) => {
                e.preventDefault();
                this.handleSearch();
            });
        }

        // State filter
        const stateSelect = document.getElementById('stateSelect');
        if (stateSelect) {
            stateSelect.addEventListener('change', (e) => {
                const stateId = e.target.value;
                if (stateId) {
                    this.loadHotels(stateId);
                } else {
                    this.loadHotels();
                }
            });
        }

        // Modal close
        const modal = document.getElementById('hotelModal');
        const closeBtn = document.querySelector('.close');
        
        if (closeBtn) {
            closeBtn.addEventListener('click', () => {
                modal.style.display = 'none';
            });
        }

        if (modal) {
            modal.addEventListener('click', (e) => {
                if (e.target === modal) {
                    modal.style.display = 'none';
                }
            });
        }

        // Booking form
        const bookingForm = document.getElementById('bookingForm');
        if (bookingForm) {
            bookingForm.addEventListener('submit', (e) => {
                e.preventDefault();
                this.handleBooking();
            });
        }

        // Date change listeners for price calculation
        const checkInDate = document.getElementById('checkInDate');
        const checkOutDate = document.getElementById('checkOutDate');
        const roomTypeSelect = document.getElementById('roomTypeSelect');

        [checkInDate, checkOutDate, roomTypeSelect].forEach(element => {
            if (element) {
                element.addEventListener('change', () => {
                    if (this.currentHotel && checkInDate.value && checkOutDate.value && roomTypeSelect.value) {
                        this.calculatePrice();
                    }
                });
            }
        });
    }

    displayStates() {
        const statesGrid = document.getElementById('statesGrid');
        const stateSelect = document.getElementById('stateSelect');
        
        if (!statesGrid && !stateSelect) return;

        // Populate states grid
        if (statesGrid) {
            statesGrid.innerHTML = this.states.map(state => `
                <div class="state-card" onclick="app.selectState(${state.id})">
                    <div class="state-image">
                        <span>${this.getStateIcon(state.name)}</span>
                    </div>
                    <div class="state-info">
                        <h3>${state.name}</h3>
                        <p>${state.description}</p>
                        <div class="price-multiplier">
                            ${state.price_multiplier}x pricing
                        </div>
                        <p class="price-note">${state.hotel_count} hotels available</p>
                    </div>
                </div>
            `).join('');
        }

        // Populate state select dropdown
        if (stateSelect) {
            stateSelect.innerHTML = `
                <option value="">All States</option>
                ${this.states.map(state => 
                    `<option value="${state.id}">${state.name} (${state.hotel_count} hotels)</option>`
                ).join('')}
            `;
        }
    }

    getStateIcon(stateName) {
        const icons = {
            'California': '🌉',
            'New York': '🗽',
            'Florida': '🏖️',
            'Texas': '🤠',
            'Nevada': '🎰',
            'Arizona': '🏜️',
            'Colorado': '🏔️',
            'Washington': '🌲'
        };
        return icons[stateName] || '🏨';
    }

    selectState(stateId) {
        this.loadHotels(stateId);
        document.getElementById('hotelsSection').scrollIntoView({ behavior: 'smooth' });
    }

    displayHotels() {
        const hotelsGrid = document.getElementById('hotelsGrid');
        if (!hotelsGrid) return;

        if (this.hotels.length === 0) {
            hotelsGrid.innerHTML = '<div class="loading">No hotels found for the selected criteria.</div>';
            return;
        }

        hotelsGrid.innerHTML = this.hotels.map(hotel => `
            <div class="hotel-card" onclick="app.showHotelDetails(${hotel.id})">
                <div class="hotel-image">
                    <span>🏨</span>
                </div>
                <div class="hotel-details">
                    <h3>${hotel.name}</h3>
                    <div class="hotel-rating">
                        <span class="stars">${this.generateStars(hotel.rating)}</span>
                        <span>${hotel.rating}/5</span>
                    </div>
                    <p>${hotel.description}</p>
                    <div class="hotel-amenities">
                        ${hotel.amenities.split(',').map(amenity => 
                            `<span class="amenity-tag">${amenity.trim()}</span>`
                        ).join('')}
                    </div>
                    <div class="hotel-price">
                        <div>
                            <div class="price">$${(hotel.base_price * hotel.price_multiplier).toFixed(0)}</div>
                            <div class="price-note">per night (${hotel.state_name})</div>
                        </div>
                        <button class="btn">View Details</button>
                    </div>
                </div>
            </div>
        `).join('');
    }

    generateStars(rating) {
        const fullStars = Math.floor(rating);
        const hasHalfStar = rating % 1 !== 0;
        let stars = '★'.repeat(fullStars);
        if (hasHalfStar) stars += '☆';
        return stars;
    }

    async showHotelDetails(hotelId) {
        try {
            const response = await fetch(`/api/hotels/${hotelId}`);
            const hotel = await response.json();
            
            if (hotel.error) {
                this.showError(hotel.error);
                return;
            }

            this.currentHotel = hotel;
            this.displayHotelModal(hotel);
        } catch (error) {
            console.error('Error loading hotel details:', error);
            this.showError('Failed to load hotel details');
        }
    }

    displayHotelModal(hotel) {
        const modal = document.getElementById('hotelModal');
        const modalContent = document.querySelector('.modal-content');
        
        modalContent.innerHTML = `
            <span class="close">&times;</span>
            <h2>${hotel.name}</h2>
            <div class="hotel-info">
                <div class="hotel-rating">
                    <span class="stars">${this.generateStars(hotel.rating)}</span>
                    <span>${hotel.rating}/5</span>
                </div>
                <p><strong>Location:</strong> ${hotel.address}</p>
                <p><strong>Phone:</strong> ${hotel.phone}</p>
                <p><strong>Email:</strong> ${hotel.email}</p>
                <p><strong>State:</strong> ${hotel.state_name} (${hotel.price_multiplier}x pricing)</p>
                <p>${hotel.description}</p>
                
                <div class="hotel-amenities">
                    <h4>Amenities:</h4>
                    ${hotel.amenities.split(',').map(amenity => 
                        `<span class="amenity-tag">${amenity.trim()}</span>`
                    ).join('')}
                </div>

                <div class="room-types">
                    <h3>Available Room Types</h3>
                    ${hotel.room_types.map(room => `
                        <div class="room-card">
                            <h4>${room.type_name}</h4>
                            <div class="room-price">$${(room.base_price * hotel.price_multiplier).toFixed(0)} per night</div>
                            <p><strong>Capacity:</strong> ${room.capacity} guests</p>
                            <p><strong>Available Rooms:</strong> ${room.available_rooms}</p>
                            <p><strong>Amenities:</strong> ${room.amenities}</p>
                        </div>
                    `).join('')}
                </div>

                <div class="booking-form">
                    <h3>Book Your Stay</h3>
                    <form id="bookingForm">
                        <div class="form-row">
                            <div class="form-group">
                                <label for="roomTypeSelect">Room Type:</label>
                                <select id="roomTypeSelect" required>
                                    <option value="">Select Room Type</option>
                                    ${hotel.room_types.filter(room => room.available_rooms > 0).map(room => 
                                        `<option value="${room.id}" data-price="${room.base_price}">
                                            ${room.type_name} - $${(room.base_price * hotel.price_multiplier).toFixed(0)}/night
                                        </option>`
                                    ).join('')}
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="numGuests">Number of Guests:</label>
                                <select id="numGuests" required>
                                    ${[1,2,3,4,5,6,7,8].map(num => `<option value="${num}">${num}</option>`).join('')}
                                </select>
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="checkInDate">Check-in Date:</label>
                                <input type="date" id="checkInDate" required min="${new Date().toISOString().split('T')[0]}">
                            </div>
                            <div class="form-group">
                                <label for="checkOutDate">Check-out Date:</label>
                                <input type="date" id="checkOutDate" required min="${new Date().toISOString().split('T')[0]}">
                            </div>
                        </div>

                        <div id="priceCalculation" class="hidden">
                            <h4>Price Breakdown:</h4>
                            <div id="priceDetails"></div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="guestName">Full Name:</label>
                                <input type="text" id="guestName" required>
                            </div>
                            <div class="form-group">
                                <label for="guestEmail">Email:</label>
                                <input type="email" id="guestEmail" required>
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="guestPhone">Phone (optional):</label>
                                <input type="tel" id="guestPhone">
                            </div>
                        </div>

                        <button type="submit" class="btn">Book Now</button>
                    </form>
                </div>
            </div>
        `;

        // Re-setup event listeners for the modal
        this.setupModalEventListeners();
        
        modal.style.display = 'block';
    }

    setupModalEventListeners() {
        const closeBtn = document.querySelector('.close');
        const modal = document.getElementById('hotelModal');
        
        if (closeBtn) {
            closeBtn.addEventListener('click', () => {
                modal.style.display = 'none';
            });
        }

        const bookingForm = document.getElementById('bookingForm');
        if (bookingForm) {
            bookingForm.addEventListener('submit', (e) => {
                e.preventDefault();
                this.handleBooking();
            });
        }

        // Price calculation listeners
        const checkInDate = document.getElementById('checkInDate');
        const checkOutDate = document.getElementById('checkOutDate');
        const roomTypeSelect = document.getElementById('roomTypeSelect');

        [checkInDate, checkOutDate, roomTypeSelect].forEach(element => {
            if (element) {
                element.addEventListener('change', () => {
                    if (this.currentHotel && checkInDate.value && checkOutDate.value && roomTypeSelect.value) {
                        this.calculatePrice();
                    }
                });
            }
        });
    }

    async calculatePrice() {
        const hotelId = this.currentHotel.id;
        const roomTypeId = document.getElementById('roomTypeSelect').value;
        const checkIn = document.getElementById('checkInDate').value;
        const checkOut = document.getElementById('checkOutDate').value;

        if (!hotelId || !roomTypeId || !checkIn || !checkOut) return;

        try {
            const response = await fetch(`/api/calculate-price?hotel_id=${hotelId}&room_type_id=${roomTypeId}&check_in=${checkIn}&check_out=${checkOut}`);
            const data = await response.json();

            if (data.error) {
                this.showError(data.error);
                return;
            }

            const priceCalculation = document.getElementById('priceCalculation');
            const priceDetails = document.getElementById('priceDetails');

            priceDetails.innerHTML = `
                <p><strong>Base Price:</strong> $${data.base_price}/night</p>
                <p><strong>State Multiplier:</strong> ${data.state_multiplier}x</p>
                <p><strong>Nightly Rate:</strong> $${data.nightly_rate}/night</p>
                <p><strong>Number of Nights:</strong> ${data.nights}</p>
                <hr>
                <p><strong>Total Price:</strong> $${data.total_price}</p>
            `;

            priceCalculation.classList.remove('hidden');
        } catch (error) {
            console.error('Error calculating price:', error);
            this.showError('Failed to calculate price');
        }
    }

    async handleBooking() {
        const formData = {
            hotel_id: this.currentHotel.id,
            room_type_id: document.getElementById('roomTypeSelect').value,
            guest_name: document.getElementById('guestName').value,
            guest_email: document.getElementById('guestEmail').value,
            guest_phone: document.getElementById('guestPhone').value,
            check_in_date: document.getElementById('checkInDate').value,
            check_out_date: document.getElementById('checkOutDate').value,
            num_guests: parseInt(document.getElementById('numGuests').value)
        };

        try {
            const response = await fetch('/api/book', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            const data = await response.json();

            if (data.error) {
                this.showError(data.error);
                return;
            }

            this.showSuccess(`Booking confirmed! Your booking ID is ${data.booking_id}. Total amount: $${data.total_price}`);
            
            // Close modal and refresh hotels
            document.getElementById('hotelModal').style.display = 'none';
            this.loadHotels();
            
        } catch (error) {
            console.error('Error creating booking:', error);
            this.showError('Failed to create booking');
        }
    }

    handleSearch() {
        const stateId = document.getElementById('stateSelect').value;
        this.loadHotels(stateId);
        document.getElementById('hotelsSection').scrollIntoView({ behavior: 'smooth' });
    }

    showError(message) {
        this.showMessage(message, 'error');
    }

    showSuccess(message) {
        this.showMessage(message, 'success');
    }

    showMessage(message, type) {
        // Remove existing messages
        const existingMessages = document.querySelectorAll('.success-message, .error-message');
        existingMessages.forEach(msg => msg.remove());

        // Create new message
        const messageDiv = document.createElement('div');
        messageDiv.className = type === 'success' ? 'success-message' : 'error-message';
        messageDiv.textContent = message;

        // Insert at top of container
        const container = document.querySelector('.container');
        if (container) {
            container.insertBefore(messageDiv, container.firstChild);
            
            // Auto-remove after 5 seconds
            setTimeout(() => {
                messageDiv.remove();
            }, 5000);
        }
    }
}

// Initialize the app when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.app = new HotelApp();
});