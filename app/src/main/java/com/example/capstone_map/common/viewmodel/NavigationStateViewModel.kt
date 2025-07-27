    package com.example.capstone_map.common.viewmodel

    import android.location.Location
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import com.example.capstone_map.common.poi.Poi
    import com.example.capstone_map.common.route.Feature
    import com.example.capstone_map.common.state.BaseState
    import com.google.gson.JsonObject


    //viewmodel들의 공유하는 데이터를 넣어놓음
    class NavigationStateViewModel : ViewModel() {


        //val = 개념 핵심: val은 참조(주소)를 바꿀 수 없다는 뜻
        val geoJsonData = MutableLiveData<String>() //
        val currentLocation = MutableLiveData<Location>()  // 현재위치를 저장하는 변수


        val navState = MutableLiveData<BaseState<*>>() // 혹은 BaseState<out ViewModel>
        val destinationText = MutableLiveData<String>()
        // POI 후보 리스트
        val poiList = MutableLiveData<List<Poi>>()
        // POI 후보리스트의  현재 index
        val currentPoiIndex = MutableLiveData<Int>()
        // 후보지들중 결정된 목적지 POI값
        val decidedDestinationPOI = MutableLiveData<Poi>()


        //navigation
        // (길안내 )보행자 경로 JSON 값 = 현재는 String
        val routeJsonData = MutableLiveData<String>()

        val routePointFeatures = MutableLiveData<List<Feature>>()  // Point 타입
        val routeLineFeatures = MutableLiveData<List<Feature>>()   // LineString 타입


        val currentAzimuth = MutableLiveData<Float>()// 현재 방위각


    }