package com.elewise.nlsvm.move2win.activities;

import android.content.Intent;

import com.elewise.nlsvm.move2win.models.Position;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by lucenko on 13.01.2018.
 */

public class SelectPositionActivity extends MapsActivity {

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        LatLng sydney = new LatLng(56.861269, 53.177131);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18f));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();

                double lat = latLng.latitude;
                double lng = latLng.longitude;
//                String format = String.format(
//                        Locale.ENGLISH,
//                        "lat: %f lng: %f",
//                        lat,
//                        lng
//                );

                mMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
//                                .title( format )
                );

                Intent data = new Intent();
                data.putExtra(Position.BUNDEL_KEY, new Position(lat, lng));
                setResult(RESULT_OK, data);


            }
        });
    }
}
