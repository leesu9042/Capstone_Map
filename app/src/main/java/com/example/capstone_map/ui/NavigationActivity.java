package com.example.capstone_map.ui;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;


import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone_map.R;
import com.example.capstone_map.voice.STTManager;
import com.example.capstone_map.voice.TTSManager;
import com.example.capstone_map.location.LocationResultListener;
import com.example.capstone_map.map.LocationToMapBinder;
import com.example.capstone_map.map.TMapInitializer;
import com.example.capstone_map.viewmodel.NavigationViewModel;
import com.skt.Tmap.TMapView;

public class NavigationActivity extends AppCompatActivity {


    private LocationResultListener userCallback;
    private STTManager sttManager;
    private TTSManager ttsManager;
    private TextView resultText;
    private Button sttButton;
    private LocationToMapBinder binder; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// activity_main.xml 레이아웃 연결
        sttButton = findViewById(R.id.stt_button);
        resultText = findViewById(R.id.result_text);

        LinearLayout mapLayout = findViewById(R.id.linearLayoutTmap);
        //지도 만들기
        TMapView tMapView = TMapInitializer.setupTMapView(this, mapLayout);




        // 상태 관찰하기” 코드야.
        // 말 그대로 "지금 앱이 어떤 상태인지 보고 있다가, 그에 따라 행동하기
        NavigationViewModel viewModel = new ViewModelProvider(this).get(NavigationViewModel.class);

        // 상태 관찰 즉, 상태가 바뀔 때마다 자동으로 실행됨!
        viewModel.getState().observe(this, state -> {
            switch (state) {
                case LISTENING:
                    ttsManager.speak("듣고 있습니다");
                    break;
                case SEARCHING_POI:
                    showLoading();
                    break;
                case SELECTING_POI:
                    showDialog();
                    break;
                case ERROR:
                    showToast("에러 발생");
                    break;
            }
        });

        // STT 결과 관찰
        viewModel.getRecognizedText().observe(this, text -> {
            resultText.setText("인식된 텍스트: " + text);
            ttsManager.speak("말씀하신 내용은 " + text + "입니다");
        });




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.stop();

        sttManager.destroy();  // STT 해제

        //tts 해제
        if (ttsManager != null) {
            ttsManager.shutdown();
            Log.d("MainActivity", "TTSManager 종료 완료!");
        }

    }


}