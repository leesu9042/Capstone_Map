package com.example.capstone_map;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;


import android.Manifest;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.capstone_map.map.LocationAutoMover;
import com.example.capstone_map.map.LocationToMapBinder;
import com.example.capstone_map.poi.Poi;
import com.example.capstone_map.poi.PoiSearchCallback;
import com.example.capstone_map.poi.PoiSearchManager;
import com.example.capstone_map.route.RouteLineDrawer;
import com.skt.Tmap.TMapView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    LocationAutoMover locationAutoMover;

    private STTManager sttManager;
    private TTSManager ttsManager;
    private TextView resultText;
    private Button sttButton;

    private double latitude;
    private double longitude;

    private String recognizedText; // STT로 인식된 텍스트 저장용 변수

    private ActivityResultLauncher<String> permissionLauncher;
    private CurrentLocationProvider currentLocationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// activity_main.xml 레이아웃 연결
        sttButton = findViewById(R.id.stt_button);
        resultText = findViewById(R.id.result_text);


        //
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        currentLocationProvider.fetchLocation(); // 권한 승인되었으니 위치 요청
                    } else {
                        Log.w("LOCATION", "사용자가 권한 거부");
                    }
                }
        );

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }


        // 2. 런타임 권한 요청 - 위치
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }

// 3. 런타임 권한 요청 - 마이크
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 200);
        }


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



        // --위치 받아오기 한번

        // LocationHelper 초기화
        currentLocationProvider = new CurrentLocationProvider(this, permissionLauncher);

// 현재 위치 요청
        currentLocationProvider.getCurrentLocation(location -> {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            Log.d("LOCATION", "위도: " + latitude + ", 경도: " + longitude);
        });


        //





        //TTS , STT

        // ✅ 1. TTS 초기화
        ttsManager = new TTSManager(this);
        ttsManager.setOnTTSReadyListener(() -> ttsManager.speak("목적지를 말씀해주세요"));

        // ✅ 2. STT 초기화
        sttManager = new STTManager(this);
        sttManager.setOnSTTResultListener(new STTManager.OnSTTResultListener() {
            @Override
            public void onSTTResult(String result) {


                recognizedText = result; // 👈 여기서 저장!

                resultText.setText("인식된 텍스트: " + result);
                ttsManager.speak("말씀하신 내용은 " + result + "입니다");





                if (latitude == 0.0 || longitude == 0.0) {
                    ttsManager.speak("아직 위치를 불러오지 못했습니다. 잠시 후 다시 시도해주세요.");
                    return;
                }


                // POI 검색 실행 (STT 결과 기반)
                PoiSearchManager.searchPois(recognizedText, latitude, longitude, new PoiSearchCallback() {
                    @Override
                    public void onSuccess(List<Poi> poiList) {
                        List<Poi> topList = poiList.subList(0, Math.min(3, poiList.size()));

                        String[] poiOptions = new String[topList.size()];
                        for (int i = 0; i < topList.size(); i++) {
                            Poi poi1 = topList.get(i);
                            poiOptions[i] = poi1.getName() + " - " + poi1.getFullAddress();
                        }

                        ttsManager.speak("검색된 장소 중 하나를 선택해주세요.");

                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("목적지를 선택하세요")
                                .setItems(poiOptions, (dialog, which) -> {
                                    Poi selectedPoi = topList.get(which);
                                    String message = "선택한 목적지는 " + selectedPoi.getName() + "입니다.";
                                    ttsManager.speak(message);

                                    // TODO: 경로 안내 시작



                                    RouteLineDrawer.drawWalkingRoute(tMapView,  longitude,latitude, "StartPoint", selectedPoi.getLongitude(), selectedPoi.getLatitude(), selectedPoi.getName(),
                                            "myRoute", Color.RED, 8);

//                                    RouteHelper.drawWalkingRoute(
//                                            tMapView,                 // 지도 객체
//                                            126.9780, 37.5665,        // 출발지: 서울시청
//                                            "서울시청",
//                                            126.9827, 37.5700,        // 도착지: 광화문
//                                            "광화문"
//                                    );



                                })
                                .setNegativeButton("취소", null)
                                .show();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e("POI_ERROR", " 검색 실패: " + errorMessage);
                        ttsManager.speak("검색에 실패했습니다.");
                    }
                });



            }

            @Override
            public void onSTTError(int errorCode) {
                ttsManager.speak("음성 인식 중 오류가 발생했습니다");
            }
        });

        // ✅ 3. 버튼 클릭 시 음성 인식 시작
        sttButton.setOnClickListener(v -> {
            ttsManager.speak("듣고 있습니다");
            sttManager.startListening();
        });



        //
//
//        permissionLauncher = registerForActivityResult(
//                new ActivityResultContracts.RequestPermission(),
//                isGranted -> {
//                    if (isGranted) {
//                        locationHelper.fetchLocation(); // 권한 승인되었으니 위치 요청
//                    } else {
//                        Log.w("LOCATION", "사용자가 권한 거부");
//                    }
//                }
//        );
//
//
//        /// 현재위치 받아오기
//
//
//
//
//
//        PoiSearchManager.searchPois(recognizedText, latitude, longitude, new PoiSearchCallback() {
//            @Override
//            public void onSuccess(List<Poi> poiList) {
//                for (Poi poi : poiList) {
//                    List<Poi> topList = poiList.subList(0, Math.min(3, poiList.size()));
//
//                    // 이름 + 주소를 배열로 추출 (Dialog에 표시용)
//                    String[] poiOptions = new String[topList.size()];
//                    for (int i = 0; i < topList.size(); i++) {
//                        Poi poi1 = topList.get(i);
//                        poiOptions[i] = poi1.getName() + " - " + poi.getFullAddress();
//                    }
//
//                    // TTS 안내
//                    ttsManager.speak("검색된 장소 중 하나를 선택해주세요.");
//
//                    // AlertDialog로 선택 유도
//                    new AlertDialog.Builder(MainActivity.this)
//                            .setTitle("목적지를 선택하세요")
//                            .setItems(poiOptions, (dialog, which) -> {
//                                Poi selectedPoi = topList.get(which);
//
//                                // 선택한 POI 처리
//                                String message = "선택한 목적지는 " + selectedPoi.getName() + "입니다.";
//                                ttsManager.speak(message);
//
//                                // TODO: 목적지로 설정하는 코드 작성
//                            })
//                            .setNegativeButton("취소", null)
//                            .show();
//                }
//            }
//
//            @Override
//            public void onFailure(String errorMessage) {
//                Log.e("POI_ERROR", " 검색 실패: " + errorMessage);
//            }
//        });





//     출발지 도착지 데이터 보내고 경로 JSON데이터 받아오는 기능 test









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

        sttManager.destroy();  // STT 해제
        ttsManager.shutdown(); // TTS 해제

    }


}