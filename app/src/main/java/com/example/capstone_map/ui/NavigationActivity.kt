package com.example.capstone_map.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View // ← ✅ 추가
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_map.R
import com.example.capstone_map.input.NavigationInputBinder
import com.example.capstone_map.ui.helper.NavigationButtonBinder
import com.example.capstone_map.ui.helper.TTSPlaybackHandler
import com.example.capstone_map.ui.state.NavigationState
import com.example.capstone_map.util.permission.PermissionHelper
import com.example.capstone_map.viewmodel.InputViewModel
import com.example.capstone_map.voice.STTListenerHelper
import com.example.capstone_map.voice.STTManager
import com.example.capstone_map.voice.TTSManager
import com.example.capstone_map.voice.TTSObserverHelper
import com.example.capstone_map.voice.VoiceManager
import java.util.LinkedList
import java.util.Queue

class NavigationActivity : AppCompatActivity() {

    private lateinit var ttsManager: TTSManager
    private lateinit var sttManager: STTManager

    private val viewModel: InputViewModel by viewModels()
    private val ttsQueue: Queue<String> = LinkedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //layout 설정
        val layout = findViewById<LinearLayout>(R.id.linearLayoutTmap)

        PermissionHelper.requestMicrophonePermission(this) // 오디오 퍼미션 추가

        // state변경 감지 바뀌면 아래 코드 실행
        viewModel.navState.observe(this) {
            Log.d("STATE_TRACKING", " 상태 변경됨: $it")
        }


        /**     TTS, STT 매니저 초기화
        *       STT 결과 수신 콜백 설정
        *       TTS 메시지 변경 감지
         *       좀더 이해가 필요
         */
        ttsManager = VoiceManager.initTTS(this)
        sttManager = VoiceManager.initSTT(this)

        // STT 결과 수신 리스너 설정
        STTListenerHelper.setListener(this, viewModel, sttManager)


        // TTS 메시지 옵저버 설정
        ttsManager.setOnTTSReadyListener {
            TTSObserverHelper.observeMessages(this, viewModel, ttsQueue) {
                TTSPlaybackHandler.speakNext(ttsManager, sttManager, viewModel, ttsQueue)
                    //TTS 메시지 큐에 쌓인 메시지를 순서대로 재생하고, 상태에 따라 후속 동작을 연결하는 함수
            }
        }

        //버튼동작과 상태로 정해둔 기능을 동작
//        NavigationButtonBinder.bind(this, viewModel, sttManager, R.id.stt_button)


        //지금은 버튼과 상태로 동작하게 만들었는데 이걸 나나중에 버튼이아니라 제스쳐로 바꾸기 쉽게
        NavigationInputBinder.bindClickAndLongPress(this, viewModel, sttManager, R.id.stt_button)


    }


    override fun onDestroy() {
        super.onDestroy()
        ttsManager.shutdown()
        sttManager.destroy()
    }







    // requestPermissions()로 권한을 요청했을 때,
    //사용자의 선택 결과가 여기로 "자동으로" 들어옴
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionHelper.handlePermissionResult(this, requestCode, permissions, grantResults)
    }
}
