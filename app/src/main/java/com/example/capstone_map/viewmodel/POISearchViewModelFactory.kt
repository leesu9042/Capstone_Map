package com.example.capstone_map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone_map.location.CurrentLocationProvider
import com.example.capstone_map.location.LocationFetcher

class POISearchViewModelFactory(
    private val stateViewModel: NavigationStateViewModel,
    private val locationFetcher: LocationFetcher
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return POISearchViewModel(stateViewModel,locationFetcher) as T
    }
}