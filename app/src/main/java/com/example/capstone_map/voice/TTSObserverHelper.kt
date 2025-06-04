package com.example.capstone_map.voice



import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_map.viewmodel.InputViewModel
import java.util.Queue



object TTSObserverHelper {

    fun observeMessages(
        activity: AppCompatActivity,
        viewModel: InputViewModel,
        ttsQueue: Queue<String>,
        onSpeakStart: () -> Unit
    ) {
        viewModel.ttsMessages.observe(activity) { messages ->
            if (!messages.isNullOrEmpty()) {
                ttsQueue.clear()
                ttsQueue.addAll(messages)
                onSpeakStart()
            }
        }
    }
}

