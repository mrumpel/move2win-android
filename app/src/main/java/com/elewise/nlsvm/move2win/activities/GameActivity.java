package com.elewise.nlsvm.move2win.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.elewise.nlsvm.move2win.R;
import com.elewise.nlsvm.move2win.models.Driver;
import com.elewise.nlsvm.move2win.models.Position;
import com.elewise.nlsvm.move2win.models.Room;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucenko on 13.01.2018.
 */

public class GameActivity extends MapsActivity implements LocationListener {

    private Location curLocation;
    private ArrayList<LatLng> latLngs;
    private Marker marker1;
    private DatabaseReference dbRef;
    private Room room;
    private boolean owner;
    private String room_name;
    private View btnReadyToStart;
    private TextView statusLabel;
    private Query roomQuery;
    private Marker marker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        statusLabel = findViewById(R.id.race_status);
        statusLabel.setVisibility(View.VISIBLE);

        btnReadyToStart = findViewById(R.id.ready_to_start);
        btnReadyToStart.setVisibility(View.VISIBLE);
        btnReadyToStart.setEnabled(false);

        btnReadyToStart.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateDriverReady();
                    }
                }
        );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);

        room_name = getIntent().getStringExtra("room_name");
        owner = getIntent().getBooleanExtra("owner", false);

        if (room_name == null) {
            Toast.makeText(this, "undefined game name", Toast.LENGTH_SHORT).show();
            return;
        }

        dbRef = FirebaseDatabase.getInstance().getReference().child("android");

        subscribeToRoomChanges();

        runGpsLocation();
    }

    private void subscribeToRoomChanges() {
        roomQuery = dbRef.orderByChild("Name").equalTo(room_name);
        roomQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(GameActivity.this, "added", Toast.LENGTH_SHORT).show();
                room = dataSnapshot.getValue(Room.class);
                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.title("Start");
                LatLng startLL = new LatLng(room.start.lat, room.start.lng);
                markerOptions.position(startLL);

                mMap.addMarker(markerOptions);

                markerOptions.title("Finish");
                LatLng finishLL = new LatLng(room.finish.lat, room.finish.lng);
                markerOptions.position(finishLL);

                latLngs = new ArrayList<>();

                latLngs.add(startLL);
                latLngs.add(finishLL);

                mMap.addMarker(markerOptions);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (LatLng latLng : latLngs) {
                    builder.include(latLng);
                }

                LatLngBounds bounds = builder.build();

                int padding = getResources().getDimensionPixelSize(R.dimen.indent_normal);
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                mMap.moveCamera(cu);

                mMap.animateCamera(cu);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                int oldStatus = room.status;

                room = dataSnapshot.getValue(Room.class);

                if(owner && room.isDriversReady() && room.status == Room.Status.Empty.ordinal()){
                    updateRoomStatus(2);
                }

                updateMarkers(room.driver1, room.driver2);

                if(oldStatus == 0 && room.status == 2){
                    statusLabel.setText("Гонка началась!!!");
                    statusLabel.setBackgroundColor(Color.RED);
                }

                if(oldStatus == 2 && room.status == Room.Status.End3.ordinal()){
                    finish();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Toast.makeText(GameActivity.this, "Игра была удалена", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(room.status != 3){
            updateRoomStatus(4);
        }
    }

    private void runGpsLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "Дайте разрешение на использоваение GPS", Toast.LENGTH_SHORT).show();
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        curLocation = location;

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        if(room!=null) {
            updateDriverPosition(latitude, longitude);

            float[] results = new float[2];

            Log.d("stef", "onLocationChanged: x: "+results[0]+", y: "+results[1]);

            if(room.status == 0) {

                Location.distanceBetween(
                        curLocation.getLatitude(),
                        curLocation.getLongitude(),
                        room.start.lat,
                        room.start.lng,
                        results
                );
                btnReadyToStart.setEnabled(results[0]<80 && results[1]<80);
            }

            if(room.status == 2){
                btnReadyToStart.setVisibility(View.GONE);

                Location.distanceBetween(
                        curLocation.getLatitude(),
                        curLocation.getLongitude(),
                        room.finish.lat,
                        room.finish.lng,
                        results
                );

                if(results[0]<50 && results[1]<50){
                    updateRoomStatus(4);
                    startActivity(new Intent(GameActivity.this, CreateRoomActivity.class));
                    finish();
                }

            }
        }
    }

    private void updateMarkers(Driver driver1, Driver driver2) {

        if(driver1!=null && driver1.curPos != null) {
            if (marker1 == null) {
                int px = getResources().getDimensionPixelSize(R.dimen.indent_large);
                Bitmap bitmap1 = Bitmap.createBitmap(px, px, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap1);
                Drawable shape = getResources().getDrawable(R.drawable.marker_one);
                shape.setBounds(0, 0, bitmap1.getWidth(), bitmap1.getHeight());
                shape.draw(canvas);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap1));
                markerOptions.position(getLatLng(driver1));
                marker1 = mMap.addMarker(markerOptions);
            } else {
                marker1.setPosition(getLatLng(driver1));
            }
        }
        
        if(driver2!=null && driver2.curPos !=null){
            if (marker2 == null) {
                int px = getResources().getDimensionPixelSize(R.dimen.indent_large);
                Bitmap bitmap1 = Bitmap.createBitmap(px, px, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap1);
                Drawable shape = getResources().getDrawable(R.drawable.marker_two);
                shape.setBounds(0, 0, bitmap1.getWidth(), bitmap1.getHeight());
                shape.draw(canvas);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(getLatLng(driver2));
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap1));
                marker2 = mMap.addMarker(markerOptions);
            } else {
                marker2.setPosition(getLatLng(driver2));
            }
        }
    }

    @NonNull
    private LatLng getLatLng(Driver driver) {
        return new LatLng(driver.curPos.lat, driver.curPos.lng);
    }

    private void updateRoomStatus(int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("Status", status);
        dbRef.child(room.name).updateChildren(map);
    }

    private void updateDriverPosition(double latitude, double longitude) {
        Map<String, Object> map = new HashMap<>();

        map.put("CurrentPosition", new Position(latitude, longitude));
        dbRef.child(room.name).child(getDriverId()).updateChildren(map);
    }

    private void updateDriverReady() {
        Map<String, Object> map = new HashMap<>();

        map.put("Ready", true);
        dbRef.child(room.name).child(getDriverId()).updateChildren(map);
    }

    @NonNull
    private String getDriverId() {
        return owner ? "Driver1" : "Driver2";
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        runGpsLocation();
    }
}
