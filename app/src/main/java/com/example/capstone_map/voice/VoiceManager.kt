package com.example.capstone_map.voice

// VoiceManager.kt
import android.content.Context
import com.example.capstone_map.voice.STTManager
import com.example.capstone_map.voice.TTSManager

object VoiceManager {
    fun initSTT(context: Context): STTManager {
        return STTManager(context as android.app.Activity)
    }

    fun initTTS(context: Context): TTSManager {
        return TTSManager(context)
    }
}
