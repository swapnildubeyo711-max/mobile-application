package com.hotelapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hotelapp.data.SortOption
import com.hotelapp.ui.components.FilterBottomSheet
import com.hotelapp.ui.components.HotelCard
import com.hotelapp.ui.components.SearchBar
import com.hotelapp.ui.theme.HotelAppTheme
import com.hotelapp.viewmodel.HotelViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: HotelViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HotelAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HotelSearchScreen(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelSearchScreen(viewModel: HotelViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showFilterSheet by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // App Title
        Text(
            text = "Hotel Finder 3D",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Search Bar
        SearchBar(
            query = searchQuery,
            onQueryChange = { query ->
                searchQuery = query
                viewModel.searchHotels(query)
            },
            placeholder = "Search cities (e.g., 'L' for London, Paris...)",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        
        // Filter and Sort Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Filter Button
            OutlinedButton(
                onClick = { showFilterSheet = true },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Filters")
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Quick Sort Buttons
            Row(modifier = Modifier.weight(2f)) {
                FilterChip(
                    onClick = { viewModel.applyFilter(sortBy = SortOption.PRICE_LOW_TO_HIGH) },
                    label = { Text("Price ↑", style = MaterialTheme.typography.bodySmall) },
                    selected = uiState.searchFilter.sortBy == SortOption.PRICE_LOW_TO_HIGH,
                    modifier = Modifier.padding(end = 4.dp)
                )
                FilterChip(
                    onClick = { viewModel.applyFilter(sortBy = SortOption.RATING) },
                    label = { Text("Rating", style = MaterialTheme.typography.bodySmall) },
                    selected = uiState.searchFilter.sortBy == SortOption.RATING
                )
            }
        }
        
        // Content
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                uiState.errorMessage != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = uiState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.retry() }) {
                            Text("Retry")
                        }
                    }
                }
                
                uiState.hotels.isEmpty() -> {
                    Text(
                        text = "No hotels found",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.hotels) { hotel ->
                            HotelCard(
                                hotel = hotel,
                                onHotelClick = { selectedHotel ->
                                    val intent = Intent(context, HotelDetailActivity::class.java).apply {
                                        putExtra("hotel", selectedHotel)
                                    }
                                    context.startActivity(intent)
                                },
                                onView3DClick = { selectedHotel ->
                                    val intent = Intent(context, Hotel3DViewActivity::class.java).apply {
                                        putExtra("hotel", selectedHotel)
                                    }
                                    context.startActivity(intent)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    
    // Filter Bottom Sheet
    if (showFilterSheet) {
        FilterBottomSheet(
            currentFilter = uiState.searchFilter,
            onDismiss = { showFilterSheet = false },
            onApplyFilter = { minPrice, maxPrice, minRating, sortBy ->
                viewModel.applyFilter(minPrice, maxPrice, minRating, sortBy)
                showFilterSheet = false
            },
            onClearFilters = {
                viewModel.clearFilters()
                showFilterSheet = false
            }
        )
    }
}