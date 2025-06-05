package com.example.capstone_map.viewmodel

import androidx.lifecycle.ViewModel
import com.example.capstone_map.ui.state.NavigationState
import com.example.capstone_map.voice.STTManager
import com.example.capstone_map.voice.TTSManager

import com.example.capstone_map.ui.state.*

class DestinationViewModel(
    private val stateViewModel: NavigationStateViewModel,
    private val ttsManager: TTSManager,
    private val sttManager: STTManager
) : ViewModel() {


    fun updateState(state: NavigationState) {
        stateViewModel.navState.value = state
        state.handle(this)
    }

    fun speak(text: String, onDone: (() -> Unit)? = null) { //함수 넘겨도되고 안 넘겨도돼
        ttsManager.speak(text, object : TTSManager.OnSpeakCallback {
            override fun onStart() {}
            override fun onDone() {
                onDone?.invoke()
            }
        })
    }

    fun getCurrentState(): NavigationState? {
        return stateViewModel.navState.value

    }


    fun getTTSManager(): TTSManager = ttsManager
    fun getSTTManager(): STTManager = sttManager






    //행동함수
    fun getDestinationText(): String {
        return stateViewModel.destinationText.value ?: "알 수 없음"
    }

    fun startListeningForDestination() {
        updateState(ListeningForDestination)
    }

    fun listenToDestination() {
        sttManager.listen(object : STTManager.OnResultCallback {
            override fun onResult(result: String) { //목적지를 들은후 text저장 + 목적지 확인하는 상태로 업데이트
                stateViewModel.destinationText.value = result
                updateState(AskingDestinationConfirmation)
            }

            override fun onError(errorMessage: String) {
                speak("죄송합니다. 문제가 발생했습니다. 버튼 다시눌러주세요") { //(제스쳐 다시실행해주세요)
                    updateState(AwaitingDestinationInput)
                }
            }
        })
    }


//3단계 목적지 확인
    fun confirmDestination() {
        updateState(DestinationRight)
    }

    fun denyDestination() {
        stateViewModel.destinationText.value = "" // 이게맞냐?
        updateState(DestinationWrong)
    }

//

    fun fetchingCandidates () {
        updateState(SearchingDestination)

    }


    fun restartDestinationInput() {
        updateState(AwaitingDestinationInput)
    }




}
