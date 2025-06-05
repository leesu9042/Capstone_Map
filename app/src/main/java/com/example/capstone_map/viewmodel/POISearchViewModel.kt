package com.example.capstone_map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.capstone_map.location.CurrentLocationProvider
import com.example.capstone_map.location.LocationFetcher
import com.example.capstone_map.ui.state.*

class POISearchViewModel(
    private val stateViewModel: NavigationStateViewModel,
    private val locationFetcher: LocationFetcher

) : ViewModel() {


    val poiSearchState = MutableLiveData<POISearchState>()
    private val candidates = mutableListOf<String>() // 예시: 실제로는 POI 모델을 써야 함
    private var currentIndex = 0

    fun updateState(state: POISearchState) {
        poiSearchState.value = state
        state.handle(this)
    }

    fun fetchCurrentLocation() {
        locationFetcher.fetchLocation { location ->
            if (location != null) {
                // 현재 위치를 stateViewModel에 저장
                stateViewModel.currentLocation.postValue(location)
                TODO("이 Location정보를 가져와서 stateModelView에 넣었고 , 다음 FetchingCandidate에서" +
                        "해당 위치와 destination으로 장소 검색해서 후보들 가져오는거 만들어야됨")
                updateState(FetchingCandidates)

            } else {
                // 위치를 가져올 수 없을 때 처리
                // 예: 사용자에게 알림 표시, 기본 위치 설정 등
            }
        }
    }



    fun fetchCandidatesFromAPI() {

    }

    fun showNextCandidate() {

    }

    fun confirmCandidate() {

    }

    fun skipCandidate() {

    }

    fun handleNoCandidates() {
        // 필요 시 목적지 입력 단계로 다시 보내기
    }


}
