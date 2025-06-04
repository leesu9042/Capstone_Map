package com.example.capstone_map.ui.helper



import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.capstone_map.ui.state.NavigationState
import com.example.capstone_map.viewmodel.InputViewModel
import com.example.capstone_map.voice.STTManager
import com.example.capstone_map.voice.TTSManager
import java.util.Queue

object TTSPlaybackHandler {

    fun speakNext(
        ttsManager: TTSManager,
        sttManager: STTManager,
        viewModel: InputViewModel,
        ttsQueue: Queue<String>
    ) {
        if (ttsQueue.isNotEmpty()) {
            val message = ttsQueue.poll()
            Log.d("TTS_FLOW", " speakNext(): 말할 메시지 = $message")

            if (ttsQueue.isEmpty()) {
                Log.d("TTS_FLOW", " 마지막 메시지입니다. 상태 확인 후 처리 시작.")

                ttsManager.setOnSpeakDoneListener {
                    val currentState = viewModel.navState.value
                    Log.d("TTS_FLOW", " onSpeakDoneListener 호출됨, 현재 상태: $currentState")

                    when (currentState) { //상태에 따라
                        NavigationState.ListeningForDestination -> {
                            Log.d("TTS_FLOW", "🎤 상태가 ListeningForDestination → STT 시작")
                            viewModel.updateStateToListening()
                            sttManager.startListening()
                        }

                        NavigationState.ConfirmingDestination -> {
                            Log.d("TTS_FLOW", "📝 상태가 ConfirmingDestination → 버튼 입력 대기")
                            viewModel.waitingForConfirmation()
                        }

                        else -> {
                            Log.d("TTS_FLOW", " 다른 상태: $currentState → 처리하지 않음")
                        }
                    }
                }
            }

            ttsManager.speak(message)
            Log.d("TTS_FLOW", "📢 TTS speak() 호출됨")

            if (ttsQueue.isNotEmpty()) {
                Handler(Looper.getMainLooper()).postDelayed({
                    speakNext(ttsManager, sttManager, viewModel, ttsQueue)
                }, 2000)
                Log.d("TTS_FLOW", "⏳ 다음 메시지를 위해 2초 지연 설정")
            }
        } else {
            Log.d("TTS_FLOW", " speakNext() 호출됐지만 큐가 비어 있음")
        }
    }
}
