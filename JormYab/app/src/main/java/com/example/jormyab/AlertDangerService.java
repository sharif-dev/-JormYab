package com.example.jormyab;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.ExecutionException;


public class AlertDangerService extends Service {


    private static final String TAG = AlertDangerService.class.getSimpleName();

    private LocationManager locationManager;
    private LocationListener locationListener;



    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final boolean[] isLocked = new boolean[1];
        final View view = View.inflate(getApplicationContext(), R.layout.activity_main, null);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @SuppressLint("ResourceType")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onLocationChanged(Location location) {
                String s = String.valueOf(location.getLongitude()) + " " + String.valueOf(location.getLatitude());
                try {
                    boolean response = new Client2().execute(s).get();
                    if (response){
                        KeyguardManager myKM = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
                        assert myKM != null;
                        isLocked[0] = myKM.inKeyguardRestrictedInputMode();

                        Toast.makeText(getBaseContext(),s, Toast.LENGTH_LONG).show();
                        if (isLocked[0]) {
                            long[] timing = new long[]{500, 500, 500, 200, 500, 500, 500, 500, 500, 200, 500, 500};
                            int[] strength = new int[]{255, 0, 255, 0, 255, 0, 255, 0, 255, 0, 255, 0};
                            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(VibrationEffect.createWaveform(timing, strength, -1));
                        }
                        else {
                            Snackbar.make(view.findViewById(R.layout.popup_snackbar), "danger place",
                                    Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                        //TODO notification
                    }else {
                        Toast.makeText(getBaseContext(),"OK", Toast.LENGTH_LONG).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println("FFF");
            return START_NOT_STICKY;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        System.out.println("TTT");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}