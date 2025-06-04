package com.example.capstone_map.action

import com.example.capstone_map.ui.state.NavigationState
import com.example.capstone_map.viewmodel.InputViewModel
import com.example.capstone_map.voice.STTManager

object NavigationStateActionHandler {

    fun handle(state: NavigationState?, viewModel: InputViewModel, sttManager: STTManager) {
        when (state) {
            null,
            NavigationState.AwaitingDestinationInput -> {
                viewModel.startNavigationPreparation()
            }

            NavigationState.ConfirmingDestination -> {
                viewModel.confirmDestination()
            }

            else -> {}
        }
    }

    fun handleLongPress(state: NavigationState?, viewModel: InputViewModel, sttManager: STTManager): Boolean {
        return if (state == NavigationState.ConfirmingDestination) {
            viewModel.retryDestinationInput()
            viewModel.updateStateToListening()
            sttManager.startListening()
            true
        } else {
            false
        }
    }
}
