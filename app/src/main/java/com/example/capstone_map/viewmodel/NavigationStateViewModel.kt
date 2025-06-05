package com.example.capstone_map.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_map.ui.state.NavigationState

class NavigationStateViewModel : ViewModel() {
    val navState = MutableLiveData<NavigationState>()
    val destinationText = MutableLiveData<String>()
}