package com.example.capstone_map.ui.input

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_map.viewmodel.DestinationViewModel
import com.example.capstone_map.voice.STTManager

fun NavigationInputBinder(
    activity: AppCompatActivity,
    desViewModel: DestinationViewModel,
    primaryButtonId: Int,
    secondaryButtonId: Int,
    tertiaryButtonId: Int
) {
    val primary = activity.findViewById<View>(primaryButtonId)
    val secondary = activity.findViewById<View>(secondaryButtonId)
    val tertiary = activity.findViewById<View>(tertiaryButtonId)

    primary.setOnClickListener {
        desViewModel.getCurrentState()?.onPrimaryInput(desViewModel)
    }

    secondary.setOnClickListener {
        desViewModel.getCurrentState()?.onSecondaryInput(desViewModel)
    }

    tertiary.setOnClickListener {
        desViewModel.getCurrentState()?.onTertiaryInput(desViewModel)
    }
}
