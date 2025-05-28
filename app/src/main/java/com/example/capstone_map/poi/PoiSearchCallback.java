package com.example.capstone_map.poi;

import java.util.List;

public interface PoiSearchCallback {
    void onSuccess(List<Poi> poiList);
    void onFailure(String errorMessage);
}
