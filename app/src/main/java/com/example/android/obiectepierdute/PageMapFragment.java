package com.example.android.obiectepierdute;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PageMapFragment extends MapFragment implements
        OnMapReadyCallback {
    private boolean needsInit=false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            needsInit=true;
        }

        getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        if (needsInit) {
            CameraUpdate center=
                    CameraUpdateFactory.newLatLng(new LatLng(40.76793169992044,
                            -73.98180484771729));
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

            map.moveCamera(center);
            map.animateCamera(zoom);
        }

        addMarker(map, 40.748963847316034, -73.96807193756104, "zzz",
                "zzz2");
        addMarker(map, 40.76866299974387, -73.98268461227417,
                "zzz", "zzz2");
        addMarker(map, 40.765136435316755, -73.97989511489868,
                "zzz", "zzz2");
        addMarker(map, 40.70686417491799, -74.01572942733765,
                "zzz", "zzz2");
    }

    private void addMarker(GoogleMap map, double lat, double lon,
                           String title, String snippet) {
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                .title(title)
                .snippet(snippet));
    }
}