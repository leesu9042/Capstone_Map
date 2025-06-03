package com.example.capstone_map.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstone_map.ui.state.NavigationState;

public class NavigationViewModel extends ViewModel {

    private final MutableLiveData<String> recognizedText = new MutableLiveData<>();
    private final MutableLiveData<NavigationState> state = new MutableLiveData<>();


    public LiveData<String> getRecognizedText() {
        return recognizedText;
    }

    public LiveData<NavigationState> getState() {
        return state;
    }

    public void onSTTResult(String text) {
        recognizedText.setValue(text);
        state.setValue(NavigationState.SEARCHING_POI);

        // POI 검색 or 위치 요청 로직은 여기에 넣거나 분리 예정
    }

    public void setState(NavigationState newState) {
        state.setValue(newState);
    }
}