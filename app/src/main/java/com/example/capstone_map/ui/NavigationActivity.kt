package com.example.capstone_map.ui
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_map.R
import com.example.capstone_map.ui.input.NavigationInputBinder
import com.example.capstone_map.ui.state.AwaitingDestinationInput
import com.example.capstone_map.util.permission.PermissionHelper
import com.example.capstone_map.viewmodel.NavigationAssembler
import com.example.capstone_map.voice.STTManager
import com.example.capstone_map.voice.TTSManager

class NavigationActivity : AppCompatActivity() {

    private lateinit var ttsManager: TTSManager
    private lateinit var sttManager: STTManager

//    private val viewModel: InputViewModel by viewModels()
    // private val ttsQueue: Queue<String> = LinkedList()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //layout 설정
        val layout = findViewById<LinearLayout>(R.id.linearLayoutTmap)

        PermissionHelper.requestMicrophonePermission(this) // 오디오 퍼미션 추가

        val assembler = NavigationAssembler(this, this) //어셈블러 선언
        val destinationViewModel = assembler.destinationViewModel

        ttsManager = assembler.ttsManager
        sttManager = assembler.sttManager

        destinationViewModel.updateState(AwaitingDestinationInput)


        // 버튼1개 바인딩 -> 버튼 3개 바인딩
        NavigationInputBinder(
            activity = this,
            desViewModel = destinationViewModel,
            primaryButtonId = R.id.gesture1,
            secondaryButtonId = R.id.gesture2,
            tertiaryButtonId = R.id.gesture3
        )



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
