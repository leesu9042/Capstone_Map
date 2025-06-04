package com.example.capstone_map.input

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_map.action.NavigationStateActionHandler
import com.example.capstone_map.viewmodel.InputViewModel
import com.example.capstone_map.voice.STTManager

object NavigationInputBinder {

    fun bindClickAndLongPress(
        activity: AppCompatActivity,
        viewModel: InputViewModel,
        sttManager: STTManager,
        buttonId: Int
    ) {
        val triggerButton = activity.findViewById<View>(buttonId)

        triggerButton.setOnClickListener {
            NavigationStateActionHandler.handle(viewModel.navState.value, viewModel, sttManager)
        }

        triggerButton.setOnLongClickListener {
            NavigationStateActionHandler.handleLongPress(viewModel.navState.value, viewModel, sttManager)
        }
    }
}
