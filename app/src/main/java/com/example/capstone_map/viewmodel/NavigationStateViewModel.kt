package com.example.capstone_map.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_map.ui.state.NavigationState

class NavigationStateViewModel : ViewModel() {
    val currentLocation = MutableLiveData<Location>()  // ✅ 올바르게 수정됨
    val navState = MutableLiveData<NavigationState>()
    val destinationText = MutableLiveData<String>()
}