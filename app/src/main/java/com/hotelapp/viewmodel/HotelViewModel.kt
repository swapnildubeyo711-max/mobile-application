package com.hotelapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotelapp.data.Hotel
import com.hotelapp.data.HotelRepository
import com.hotelapp.data.SearchFilter
import com.hotelapp.data.SortOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class HotelUiState(
    val hotels: List<Hotel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchFilter: SearchFilter = SearchFilter()
)

class HotelViewModel(
    private val repository: HotelRepository = HotelRepository()
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HotelUiState())
    val uiState: StateFlow<HotelUiState> = _uiState.asStateFlow()
    
    init {
        searchHotels()
    }
    
    fun searchHotels(query: String = "") {
        val currentFilter = _uiState.value.searchFilter.copy(query = query)
        searchWithFilter(currentFilter)
    }
    
    fun applyFilter(
        minPrice: Double? = null,
        maxPrice: Double? = null,
        minRating: Float? = null,
        sortBy: SortOption? = null
    ) {
        val currentFilter = _uiState.value.searchFilter
        val newFilter = currentFilter.copy(
            minPrice = minPrice,
            maxPrice = maxPrice,
            minRating = minRating,
            sortBy = sortBy ?: currentFilter.sortBy
        )
        searchWithFilter(newFilter)
    }
    
    fun clearFilters() {
        val newFilter = SearchFilter(query = _uiState.value.searchFilter.query)
        searchWithFilter(newFilter)
    }
    
    private fun searchWithFilter(filter: SearchFilter) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                searchFilter = filter
            )
            
            repository.searchHotels(filter)
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Unknown error occurred"
                    )
                }
                .collect { hotels ->
                    _uiState.value = _uiState.value.copy(
                        hotels = hotels,
                        isLoading = false,
                        errorMessage = null
                    )
                }
        }
    }
    
    fun getHotelById(id: String): Hotel? {
        return repository.getHotelById(id)
    }
    
    fun getCities(): List<String> {
        return repository.getCities()
    }
    
    fun retry() {
        searchWithFilter(_uiState.value.searchFilter)
    }
}