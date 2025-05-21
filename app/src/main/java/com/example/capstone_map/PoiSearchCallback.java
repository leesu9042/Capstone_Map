package com.example.capstone_map;

import java.util.List;

public interface PoiSearchCallback {
    void onSuccess(List<Poi> poiList);
    void onFailure(String errorMessage);
}
