package com.hotelapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hotel(
    val id: String,
    val name: String,
    val city: String,
    val country: String,
    val address: String,
    val price: Double,
    val currency: String = "USD",
    val rating: Float,
    val reviewCount: Int,
    val images: List<String>,
    val amenities: List<String>,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val starRating: Int,
    val priceComparison: List<PriceComparison> = emptyList(),
    val is3DAvailable: Boolean = true
) : Parcelable

@Parcelize
data class PriceComparison(
    val source: String,
    val price: Double,
    val url: String
) : Parcelable

data class SearchFilter(
    val query: String = "",
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val minRating: Float? = null,
    val sortBy: SortOption = SortOption.PRICE_LOW_TO_HIGH
)

enum class SortOption {
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW,
    RATING,
    DISTANCE,
    POPULARITY
}