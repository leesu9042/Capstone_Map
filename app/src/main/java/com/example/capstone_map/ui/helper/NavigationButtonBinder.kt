package com.example.capstone_map.ui.helper


import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_map.ui.state.NavigationState
import com.example.capstone_map.viewmodel.InputViewModel
import com.example.capstone_map.voice.STTManager

object NavigationButtonBinder {

    fun bind(
        activity: AppCompatActivity,
        viewModel: InputViewModel,
        sttManager: STTManager,
        buttonId: Int
    ) {
        val sttButton = activity.findViewById<View>(buttonId)

        // ✅ 짧게 클릭: 상태에 따라 초기화 or 확정
        sttButton.setOnClickListener {
            when (viewModel.navState.value) {
                null,
                NavigationState.AwaitingDestinationInput -> {
                    viewModel.startNavigationPreparation()
                }

                NavigationState.ConfirmingDestination -> {
                    viewModel.confirmDestination()
                }

                else -> {
                    // 필요한 다른 상태 핸들링
                }
            }
        }

        // ✅ 길게 클릭: 목적지 다시 말하게 하기
        sttButton.setOnLongClickListener {
            if (viewModel.navState.value == NavigationState.ConfirmingDestination) {
                viewModel.retryDestinationInput()
                viewModel.updateStateToListening()
                sttManager.startListening()
                true
            } else {
                false
            }
        }
    }
}
