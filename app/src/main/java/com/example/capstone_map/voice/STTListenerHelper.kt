package com.example.capstone_map.voice



import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_map.viewmodel.InputViewModel
import com.example.capstone_map.voice.STTManager

object STTListenerHelper {

    fun setListener(
        activity: AppCompatActivity,
        viewModel: InputViewModel,
        sttManager: STTManager
    ) {
        sttManager.setOnSTTResultListener(object : STTManager.OnSTTResultListener {
            override fun onSTTResult(result: String) {
                android.util.Log.d("STT_RESULT", "사용자 음성 입력: $result")
                Toast.makeText(activity, "입력된 목적지: $result", Toast.LENGTH_SHORT).show()

                // ViewModel 처리: 목적지 저장 및 상태 전이
                viewModel.processDestination(result)

                // (2) ListeningForDestination → (3) ConfirmingDestination
                // + TTS 메시지로 확인 발화 (viewModel이 메시지를 변경)
            }

            override fun onSTTError(errorCode: Int) {
                Toast.makeText(activity, "음성 인식 실패: 오류 코드 $errorCode", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

