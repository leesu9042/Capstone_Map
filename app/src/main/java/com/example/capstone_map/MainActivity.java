package com.example.capstone_map;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.skt.Tmap.TMapView;

public class MainActivity extends AppCompatActivity {

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




    }
}