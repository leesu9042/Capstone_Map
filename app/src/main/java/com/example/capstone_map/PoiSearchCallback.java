package com.example.capstone_map;

public interface PoiSearchCallback {
    void onSuccess(String responseJson);
    // 👉 성공했을 때 결과 JSON 문자열을 넘겨주는 메서드

    void onFailure(String errorMessage);
    // 👉 실패했을 때 에러 메시지를 넘겨주는 메서드
}