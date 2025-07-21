package com.hotelapp.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class HotelRepository {
    
    private val mockHotels = listOf(
        // London Hotels
        Hotel(
            id = "1",
            name = "The Ritz London",
            city = "London",
            country = "United Kingdom",
            address = "150 Piccadilly, St. James's, London W1J 9BR",
            price = 650.0,
            rating = 4.8f,
            reviewCount = 2847,
            images = listOf(
                "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800",
                "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=800",
                "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800"
            ),
            amenities = listOf("WiFi", "Spa", "Restaurant", "Bar", "Concierge", "Room Service", "Fitness Center"),
            description = "Iconic luxury hotel in the heart of London with world-class service and elegant accommodations.",
            latitude = 51.5074,
            longitude = -0.1278,
            starRating = 5,
            priceComparison = listOf(
                PriceComparison("Booking.com", 680.0, "https://booking.com"),
                PriceComparison("Expedia", 665.0, "https://expedia.com"),
                PriceComparison("Hotels.com", 675.0, "https://hotels.com")
            )
        ),
        Hotel(
            id = "2",
            name = "Claridge's",
            city = "London",
            country = "United Kingdom",
            address = "Brook St, Mayfair, London W1K 4HR",
            price = 750.0,
            rating = 4.9f,
            reviewCount = 1923,
            images = listOf(
                "https://images.unsplash.com/photo-1571896349842-33c89424de2d?w=800",
                "https://images.unsplash.com/photo-1564501049412-61c2a3083791?w=800"
            ),
            amenities = listOf("WiFi", "Spa", "Restaurant", "Bar", "Concierge", "Butler Service", "Afternoon Tea"),
            description = "Art Deco masterpiece in Mayfair, epitomizing British elegance and sophistication.",
            latitude = 51.5130,
            longitude = -0.1472,
            starRating = 5,
            priceComparison = listOf(
                PriceComparison("Booking.com", 780.0, "https://booking.com"),
                PriceComparison("Expedia", 765.0, "https://expedia.com")
            )
        ),
        
        // Paris Hotels
        Hotel(
            id = "3",
            name = "Hotel Ritz Paris",
            city = "Paris",
            country = "France",
            address = "15 Place Vendôme, 75001 Paris",
            price = 850.0,
            rating = 4.9f,
            reviewCount = 3241,
            images = listOf(
                "https://images.unsplash.com/photo-1549294413-26f195200c16?w=800",
                "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=800"
            ),
            amenities = listOf("WiFi", "Spa", "Restaurant", "Bar", "Shopping", "Garden", "Pool"),
            description = "Legendary palace hotel on Place Vendôme, synonymous with Parisian luxury and glamour.",
            latitude = 48.8566,
            longitude = 2.3522,
            starRating = 5,
            priceComparison = listOf(
                PriceComparison("Booking.com", 890.0, "https://booking.com"),
                PriceComparison("Expedia", 875.0, "https://expedia.com"),
                PriceComparison("Hotels.com", 885.0, "https://hotels.com")
            )
        ),
        Hotel(
            id = "4",
            name = "Le Bristol Paris",
            city = "Paris",
            country = "France",
            address = "112 Rue du Faubourg Saint-Honoré, 75008 Paris",
            price = 720.0,
            rating = 4.8f,
            reviewCount = 2156,
            images = listOf(
                "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=800",
                "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800"
            ),
            amenities = listOf("WiFi", "Spa", "Restaurant", "Garden", "Pool", "Pet-Friendly", "Shopping"),
            description = "Palace hotel with French elegance, featuring a rooftop pool and Michelin-starred dining.",
            latitude = 48.8720,
            longitude = 2.3176,
            starRating = 5,
            priceComparison = listOf(
                PriceComparison("Booking.com", 750.0, "https://booking.com"),
                PriceComparison("Expedia", 735.0, "https://expedia.com")
            )
        ),
        
        // New York Hotels
        Hotel(
            id = "5",
            name = "The Plaza Hotel",
            city = "New York",
            country = "United States",
            address = "768 5th Ave, New York, NY 10019",
            price = 595.0,
            rating = 4.7f,
            reviewCount = 4523,
            images = listOf(
                "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=800",
                "https://images.unsplash.com/photo-1571896349842-33c89424de2d?w=800"
            ),
            amenities = listOf("WiFi", "Spa", "Restaurant", "Bar", "Shopping", "Concierge", "Business Center"),
            description = "Iconic luxury hotel overlooking Central Park, a New York City landmark since 1907.",
            latitude = 40.7614,
            longitude = -73.9776,
            starRating = 5,
            priceComparison = listOf(
                PriceComparison("Booking.com", 625.0, "https://booking.com"),
                PriceComparison("Expedia", 610.0, "https://expedia.com"),
                PriceComparison("Hotels.com", 615.0, "https://hotels.com")
            )
        ),
        Hotel(
            id = "6",
            name = "The St. Regis New York",
            city = "New York",
            country = "United States",
            address = "2 E 55th St, New York, NY 10022",
            price = 675.0,
            rating = 4.8f,
            reviewCount = 3847,
            images = listOf(
                "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800",
                "https://images.unsplash.com/photo-1549294413-26f195200c16?w=800"
            ),
            amenities = listOf("WiFi", "Spa", "Restaurant", "Bar", "Butler Service", "Business Center", "Pet-Friendly"),
            description = "Timeless elegance in Midtown Manhattan with impeccable service and luxurious accommodations.",
            latitude = 40.7614,
            longitude = -73.9776,
            starRating = 5,
            priceComparison = listOf(
                PriceComparison("Booking.com", 705.0, "https://booking.com"),
                PriceComparison("Expedia", 690.0, "https://expedia.com")
            )
        ),
        
        // Los Angeles Hotels
        Hotel(
            id = "7",
            name = "The Beverly Hills Hotel",
            city = "Los Angeles",
            country = "United States",
            address = "9641 Sunset Blvd, Beverly Hills, CA 90210",
            price = 525.0,
            rating = 4.6f,
            reviewCount = 2934,
            images = listOf(
                "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=800",
                "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=800"
            ),
            amenities = listOf("WiFi", "Pool", "Spa", "Restaurant", "Bar", "Tennis", "Fitness Center"),
            description = "The Pink Palace of Beverly Hills, a legendary hotel frequented by Hollywood stars.",
            latitude = 34.0522,
            longitude = -118.2437,
            starRating = 5,
            priceComparison = listOf(
                PriceComparison("Booking.com", 550.0, "https://booking.com"),
                PriceComparison("Expedia", 535.0, "https://expedia.com"),
                PriceComparison("Hotels.com", 545.0, "https://hotels.com")
            )
        ),
        Hotel(
            id = "8",
            name = "Chateau Marmont",
            city = "Los Angeles",
            country = "United States",
            address = "8221 Sunset Blvd, West Hollywood, CA 90046",
            price = 455.0,
            rating = 4.5f,
            reviewCount = 1876,
            images = listOf(
                "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800",
                "https://images.unsplash.com/photo-1564501049412-61c2a3083791?w=800"
            ),
            amenities = listOf("WiFi", "Pool", "Restaurant", "Bar", "Garden", "Pet-Friendly", "Parking"),
            description = "Gothic Revival castle-style hotel, a discreet hideaway for celebrities and creatives.",
            latitude = 34.0969,
            longitude = -118.3861,
            starRating = 4,
            priceComparison = listOf(
                PriceComparison("Booking.com", 480.0, "https://booking.com"),
                PriceComparison("Expedia", 465.0, "https://expedia.com")
            )
        ),
        
        // Tokyo Hotels
        Hotel(
            id = "9",
            name = "The Ritz-Carlton Tokyo",
            city = "Tokyo",
            country = "Japan",
            address = "9-7-1 Akasaka, Minato City, Tokyo 107-6245",
            price = 485.0,
            rating = 4.7f,
            reviewCount = 2654,
            images = listOf(
                "https://images.unsplash.com/photo-1571896349842-33c89424de2d?w=800",
                "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=800"
            ),
            amenities = listOf("WiFi", "Spa", "Restaurant", "Bar", "Fitness Center", "Club Lounge", "Concierge"),
            description = "Luxury hotel on the top floors of Tokyo Midtown with stunning city views.",
            latitude = 35.6762,
            longitude = 139.6503,
            starRating = 5,
            priceComparison = listOf(
                PriceComparison("Booking.com", 515.0, "https://booking.com"),
                PriceComparison("Expedia", 500.0, "https://expedia.com"),
                PriceComparison("Hotels.com", 510.0, "https://hotels.com")
            )
        ),
        
        // Dubai Hotels
        Hotel(
            id = "10",
            name = "Burj Al Arab",
            city = "Dubai",
            country = "United Arab Emirates",
            address = "Jumeirah Beach Rd, Dubai",
            price = 1250.0,
            rating = 4.9f,
            reviewCount = 5632,
            images = listOf(
                "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800",
                "https://images.unsplash.com/photo-1549294413-26f195200c16?w=800"
            ),
            amenities = listOf("WiFi", "Spa", "Restaurant", "Bar", "Pool", "Beach", "Helicopter Pad", "Butler Service"),
            description = "The world's most luxurious hotel, an architectural marvel on its own artificial island.",
            latitude = 25.2048,
            longitude = 55.2708,
            starRating = 7,
            priceComparison = listOf(
                PriceComparison("Booking.com", 1280.0, "https://booking.com"),
                PriceComparison("Expedia", 1265.0, "https://expedia.com"),
                PriceComparison("Hotels.com", 1275.0, "https://hotels.com")
            )
        )
    )
    
    fun searchHotels(filter: SearchFilter): Flow<List<Hotel>> = flow {
        delay(1000) // Simulate network delay
        
        var filteredHotels = mockHotels
        
        // Filter by search query (city name starting with letter)
        if (filter.query.isNotBlank()) {
            filteredHotels = filteredHotels.filter { hotel ->
                hotel.city.lowercase().startsWith(filter.query.lowercase()) ||
                hotel.name.lowercase().contains(filter.query.lowercase())
            }
        }
        
        // Filter by price range
        filter.minPrice?.let { minPrice ->
            filteredHotels = filteredHotels.filter { it.price >= minPrice }
        }
        
        filter.maxPrice?.let { maxPrice ->
            filteredHotels = filteredHotels.filter { it.price <= maxPrice }
        }
        
        // Filter by rating
        filter.minRating?.let { minRating ->
            filteredHotels = filteredHotels.filter { it.rating >= minRating }
        }
        
        // Sort hotels
        filteredHotels = when (filter.sortBy) {
            SortOption.PRICE_LOW_TO_HIGH -> filteredHotels.sortedBy { it.price }
            SortOption.PRICE_HIGH_TO_LOW -> filteredHotels.sortedByDescending { it.price }
            SortOption.RATING -> filteredHotels.sortedByDescending { it.rating }
            SortOption.DISTANCE -> filteredHotels.sortedBy { Random.nextDouble() } // Mock distance sorting
            SortOption.POPULARITY -> filteredHotels.sortedByDescending { it.reviewCount }
        }
        
        emit(filteredHotels)
    }
    
    fun getHotelById(id: String): Hotel? {
        return mockHotels.find { it.id == id }
    }
    
    fun getCities(): List<String> {
        return mockHotels.map { it.city }.distinct().sorted()
    }
}