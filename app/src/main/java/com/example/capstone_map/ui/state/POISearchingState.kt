package com.example.capstone_map.ui.state

import com.example.capstone_map.viewmodel.DestinationViewModel


import com.example.capstone_map.viewmodel.POISearchViewModel


sealed interface POISearchState : BaseState<POISearchViewModel>




// 1. 내 위치 가져오기
object GetCurrentLocation : POISearchState {
    override fun handle(viewModel: POISearchViewModel) {
        viewModel.fetchCurrentLocation()
    }
}



// 1. 장소 검색 시작
object FetchingCandidates : POISearchState {
    override fun handle(viewModel: POISearchViewModel) {
        viewModel.fetchCandidatesFromAPI()
    }
}

// 2. 후보지 보여주는 중
object ShowingCandidates : POISearchState {
    override fun handle(viewModel: POISearchViewModel) {

    }

    override fun onPrimaryInput(viewModel: POISearchViewModel) {

    }

    override fun onSecondaryInput(viewModel: POISearchViewModel) {

    }

    override fun onTertiaryInput(viewModel: POISearchViewModel) {

    }

}

// 3. 후보 없음
object NoCandidatesFound : POISearchState {
    override fun handle(viewModel: POISearchViewModel) {
        viewModel.handleNoCandidates()
    }
}
