package com.example.capstone_map.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_map.ui.state.NavigationState

class InputViewModel : ViewModel() {

    private val _navState = MutableLiveData<NavigationState>()
    val navState: LiveData<NavigationState> = _navState

    private val _ttsMessages = MutableLiveData<List<String>>()
    val ttsMessages: LiveData<List<String>> = _ttsMessages

    private val _recognizedDestination = MutableLiveData<String>() // ✅ 추가
    val recognizedDestination: LiveData<String> = _recognizedDestination

    fun startNavigationPreparation() {
        _navState.value = NavigationState.ListeningForDestination
        _ttsMessages.value = listOf("목적지를 말씀해 주세요. 메뉴로 돌아가려면 화면을 왼쪽으로 스와이프해주세요.")
    }

    fun updateStateToListening() {
        _navState.value = NavigationState.ListeningForDestination
    }

    fun processDestination(destination: String) {
        _recognizedDestination.value = destination // ✅ 여기에 값을 저장
        _navState.value = NavigationState.ConfirmingDestination
        _ttsMessages.value = listOf("$destination 이 맞나요? 한번 누르면 확정, 길게 누르면 다시 말할 수 있어요.")
    }

    fun confirmDestination() {
        _navState.value = NavigationState.FetchingCandidates
        _ttsMessages.value = listOf("목적지를 확정했어요. 안내를 시작합니다.")
    }

    fun retryDestinationInput() {
        _navState.value = NavigationState.ListeningForDestination
        _ttsMessages.value = listOf("다시 목적지를 말씀해주세요.")
    }

    fun waitingForConfirmation() {
        // 확인 상태 유지 & 다시 한번 안내멘트 줄 수도 있음
        _navState.value = NavigationState.ConfirmingDestination
        _ttsMessages.value = listOf("맞으면 버튼을 눌러주세요. 다시 말하려면 길게 누르세요.")
    }
}
