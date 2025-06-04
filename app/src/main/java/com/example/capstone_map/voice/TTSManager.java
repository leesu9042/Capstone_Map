package com.example.capstone_map.voice;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.Locale;

/**
 * TTSManager는 TextToSpeech 기능을 담당하는 클래스입니다.
 * 텍스트를 음성으로 변환하여 사용자에게 안내하거나 정보를 전달할 수 있습니다.
 */
public class TTSManager {
    private TextToSpeech tts; // 안드로이드 TTS 객체
    private boolean isInitialized = false;
    private OnTTSReadyListener readyListener;
    private OnSpeakDoneListener speakDoneListener;

    // 1. 초기화 완료 콜백 인터페이스
    public interface OnTTSReadyListener {
        void onReady();
    }

    // 2. 말 다 끝났을 때 콜백 인터페이스
    public interface OnSpeakDoneListener {
        void onDone();
    }

    // 3. 콜백 등록 함수
    public void setOnTTSReadyListener(OnTTSReadyListener listener) {
        this.readyListener = listener;
        if (isInitialized && listener != null) {
            listener.onReady();
        }
    }

    public void setOnSpeakDoneListener(OnSpeakDoneListener listener) {
        this.speakDoneListener = listener;
    }

    // 4. 생성자 - TTS 초기화
    public TTSManager(Context context) {


        tts = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.KOREAN);

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTSManager", "TTS: 한국어가 지원되지 않음");
                } else {
                    isInitialized = true;

                    // 🎯 여기서 리스너도 등록!
                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                            Log.d("TTS", "음성 시작됨");
                        }

                        @Override
                        public void onDone(String utteranceId) {
                            if ("TTS_ID".equals(utteranceId) && speakDoneListener != null) {
                                // 메인 스레드에서 실행 (UI 안전)
                                new Handler(Looper.getMainLooper()).post(() -> speakDoneListener.onDone());
                            }
                        }

                        @Override
                        public void onError(String utteranceId) {
                            Log.e("TTS", "TTS 오류 발생");
                        }
                    });

                    if (readyListener != null) {
                        readyListener.onReady();
                    }
                }
            } else {
                Log.e("TTSManager", "TTS 초기화 실패");
            }
        });
    }

    // 5. 텍스트 읽기
    public void speak(String text) {
        if (isInitialized) {
            // 🎯 utteranceId 넣어야 onDone()이 호출됨
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTS_ID");
        } else {
            Log.e("TTSManager", "TTS가 초기화되지 않았습니다.");
        }
    }

    // 6. 리소스 해제
    public void shutdown() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
