package com.example.capstone_map;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * TTSManager는 TextToSpeech 기능을 담당하는 클래스입니다.
 * 텍스트를 음성으로 변환하여 사용자에게 안내하거나 정보를 전달할 수 있습니다.
 */
public class TTSManager {
    private TextToSpeech tts;   // 안드로이드의 TTS 객체
    private boolean isInitialized = false;  // 초기화 여부 체크용 변수
    private OnTTSReadyListener readyListener;  // 초기화 완료 시 실행될 콜백 인터페이스

    /**
     * 초기화 완료 리스너 인터페이스
     */
    public interface OnTTSReadyListener {
        void onReady();
    }

    /**
     * 생성자
     * - TTS 객체를 생성하고 초기화함
     * - 초기화가 완료되면 한국어(Locale.KOREAN)로 언어 설정을 시도함
     *
     * @param context: 현재 Activity나 Application의 Context
     */
    public TTSManager(Context context) {
        tts = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                // 언어를 한국어로 설정
                int result = tts.setLanguage(Locale.KOREAN);

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTSManager", "TTS: 한국어가 지원되지 않음");
                } else {
                    isInitialized = true;
                    if (readyListener != null) {
                        readyListener.onReady(); // 초기화 완료 후 콜백 실행
                    }
                }
            } else {
                Log.e("TTSManager", "TTS 초기화 실패");
            }
        });
    }

    /**
     * 초기화 완료 콜백 등록 함수
     * @param listener 초기화 완료 시 호출될 리스너
     */
    public void setOnTTSReadyListener(OnTTSReadyListener listener) {
        this.readyListener = listener;
        if (isInitialized && listener != null) {
            listener.onReady(); // 이미 초기화된 상태면 즉시 실행
        }
    }

    /**
     * 텍스트를 음성으로 읽어주는 함수
     * 초기화가 완료된 경우에만 동작함
     *
     * @param text: 읽을 텍스트 (한글 지원)
     */
    public void speak(String text) {
        if (isInitialized) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            Log.e("TTSManager", "TTS가 초기화되지 않았습니다.");
        }
    }

    /**
     * shutdown 함수
     * TTS 사용 종료 후 리소스 해제 메서드
     * Activity 종료 시 호출 권장
     */
    public void shutdown() {
        if (tts != null) {
            tts.stop();         // 현재 읽고 있는 음성 중지
            tts.shutdown();     // TTS 엔진 종료
        }
    }
}

/*
TTSManager 사용 예시:

// 예: MainActivity.java
import com.example.aieyes.utils.TTSManager;

public class MainActivity extends AppCompatActivity {

    private TTSManager ttsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // 1. TTSManager 초기화 (반드시 Context를 넘겨야 함)
        ttsManager = new TTSManager(this);

        // 2. 버튼 클릭 시 음성 출력
        Button speakBtn = findViewById(R.id.speak_button);
        speakBtn.setOnClickListener(v -> {
            ttsManager.speak("안녕하세요. 음성 안내를 시작합니다.");
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 3. Activity 종료 시 반드시 shutdown 호출 (메모리 누수 방지)
        ttsManager.shutdown();
    }
}
*/
