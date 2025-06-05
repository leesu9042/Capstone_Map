package com.example.capstone_map.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone_map.voice.STTManager
import com.example.capstone_map.voice.TTSManager

class DestinationViewModelFactory(
    private val stateViewModel: NavigationStateViewModel,
    private val ttsManager: TTSManager,
    private val sttManager: STTManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DestinationViewModel(stateViewModel, ttsManager, sttManager) as T
    }
}
