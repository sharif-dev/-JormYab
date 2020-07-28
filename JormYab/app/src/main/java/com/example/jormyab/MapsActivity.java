package com.example.jormyab;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng tehran = new LatLng(35.6892, 51.3890);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tehran, (float) 10));


        final Client client = new Client();
        client.setCommand("map 1 51.3890 35.6892\n");  /** map depth longitude latitude\n**/
        client.setMap(mMap);
        client.execute();


        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                flag = true;
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (flag){
                    mMap.clear();
                    Client client1 = new Client();
                    LatLng center = mMap.getCameraPosition().target;
                    String command = "map ";
                    if (mMap.getCameraPosition().zoom < 12){
                        command += "1 ";
                    }else if (mMap.getCameraPosition().zoom < 13.5){
                        command += "2 ";

                    }else if (mMap.getCameraPosition().zoom < 16){
                        command += "3 ";
                    }else {
                        command += "4 ";
                    }
                    command += String.valueOf(center.longitude) + " " + String.valueOf(center.latitude) + "\n";
                    client1.setCommand(command);
                    client1.setMap(mMap);
                    try {
                        client1.execute();
                    }catch (Exception e){

                    }
                }
                flag = false;
            }
        });


    }





}