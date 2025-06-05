package com.example.capstone_map.ui.input

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_map.viewmodel.DestinationViewModel
import com.example.capstone_map.voice.STTManager

object NavigationInputBinder {

    fun bindClickAndLongPress(
        activity: AppCompatActivity,
        desViewModel: DestinationViewModel,
        sttManager: STTManager,
        buttonId: Int
    ) {
        val triggerButton = activity.findViewById<View>(buttonId)

        triggerButton.setOnClickListener {
            desViewModel.getCurrentState()?.onClick(desViewModel)
        }

        triggerButton.setOnLongClickListener {
            desViewModel.getCurrentState()?.onLongClick(desViewModel)
            true
        }
    }
}
