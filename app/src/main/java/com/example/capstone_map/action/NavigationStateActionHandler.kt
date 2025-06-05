//package com.example.capstone_map.action
//
//import com.example.capstone_map.ui.state.AwaitingDestinationInput
//import com.example.capstone_map.ui.state.NavigationState
//import com.example.capstone_map.viewmodel.DestinationViewModel
//
//import com.example.capstone_map.viewmodel.NavigationStateViewModel
//import com.example.capstone_map.voice.STTManager
//object NavigationStateActionHandler {
//
//    fun handle(state: NavigationState?, viewModel: DestinationViewModel, sttManager: STTManager) {
//        when (state) {
//            AwaitingDestinationInput -> {
//                // 예: 초기 시작
//                viewModel.updateState(ListeningForDestination)
//            }
//
//            ConfirmingDestination -> {
//                // 목적지 확정
//                viewModel.confirmDestination()
//            }
//
//            else -> { /* do nothing */ }
//        }
//    }
//
//    fun handleLongPress(state: NavigationState?, viewModel: DestinationViewModel, sttManager: STTManager): Boolean {
//        return if (state == ConfirmingDestination) {
//            viewModel.retryDestinationInput() // 목적지 다시 듣기
//            true
//        } else {
//            false
//        }
//    }
//}
