package com.example.capstone_map.map;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.example.capstone_map.location.LocationTracker;
import com.example.capstone_map.location.LocationUpdateCallback;
import com.skt.Tmap.TMapView;

public class LocationAutoMover implements LocationUpdateCallback {

    private final LocationTracker locationTracker;
    private final TMapView tMapView;

    public LocationAutoMover(Context context, TMapView tMapView) {
        this.tMapView = tMapView;
        this.locationTracker = new LocationTracker(context, this);
    }

    public void start() {
        locationTracker.startTracking();
    }

    public void stop() {
        locationTracker.stopTracking();
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        Log.d("GPS", "위치 변경됨: " + lat + ", " + lon);
        tMapView.setCenterPoint(lon, lat); // 자동 중심 이동

    }

    @Override
    public void onLocationAccuracyChanged(float accuracy) {}

    @Override
    public void onGPSSignalWeak() {}

    @Override
    public void onGPSSignalRestored() {}
}
