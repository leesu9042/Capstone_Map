package com.example.capstone_map;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.LinearLayout;


import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.capstone_map.navigation.LocationAutoMover;
import com.example.capstone_map.navigation.LocationToMapBinder;
import com.example.capstone_map.route.RouteHelper;
import com.skt.Tmap.TMapView;

public class MainActivity extends AppCompatActivity {

    LocationAutoMover locationAutoMover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// activity_main.xml 레이아웃 연결

        // ① 레이아웃에서 지도 추가할 공간 찾기
        LinearLayout mapLayout = findViewById(R.id.linearLayoutTmap);
        // ② TMapView 객체 생성
        TMapView tMapView = new TMapView(this);
        // ③ AppKey 설정
        tMapView.setSKTMapApiKey("MUUgFleM6h4uFPz6yYOW03Gbzskx5Gci1rdtifFf");

        tMapView.setCenterPoint(126.97800, 37.56650); // 서울 강남구 좌표
        tMapView.setZoomLevel(12);

        // ④ TMapView를 레이아웃에 붙이기
        mapLayout.addView(tMapView);


        // 출발지 도착지 데이터 보내고 경로 JSON데이터 받아오는 기능 test
//        RouteHelper.drawWalkingRoute(
//                tMapView,                 // 지도 객체
//                126.9780, 37.5665,        // 출발지: 서울시청
//                "서울시청",
//                126.9827, 37.5700,        // 도착지: 광화문
//                "광화문"
//        );

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }



        LocationToMapBinder binder = new LocationToMapBinder(this, tMapView);
        binder.start(); // 위치 추적 및 지도 표시 시작

        // LocationAutoMover 연결
        locationAutoMover = new LocationAutoMover(this, tMapView);
        locationAutoMover.start();  // 위치 추적 + 지도 중심 자동 이동 시작
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationAutoMover.stop(); // 추적 중단
    }


}