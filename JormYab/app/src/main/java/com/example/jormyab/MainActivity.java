package com.example.jormyab;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    SharedPreferences setting;
    public static View view;
    CoordinatorLayout crdLayout;

//    @SuppressLint("ResourceType")
//    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Snackbar.make(findViewById(R.layout.popup_snackbar), "danger place",
//                Snackbar.LENGTH_SHORT)
//                .show();
//        long[] timing = new long[]{700, 500, 200, 40, 700, 500, 200, 50, 200, 50, 200, 50,200,50,200,50,700,50};
//        int[] strength = new int[]{255, 0, 255, 0, 255, 0, 255, 0, 255, 0, 255, 0,255,0,255,0,255,0};
//        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(VibrationEffect.createWaveform(timing, strength, -1));
//        Intent i = new Intent(this, AlertDangerService.class);
//        System.out.println("hi");
//        startService(i);
//        System.out.println("bye");
//        setting = PreferenceManager.getDefaultSharedPreferences(this);
        if (true) {
            Intent i = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(i);
            MainActivity.this.finish();
        }
        else {
//            ProfileActivity profileActivity = new ProfileActivity();
//            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container , profileActivity).addToBackStack(null).commit();
            SubmitCrimeFragment submitCrimeFragment = new SubmitCrimeFragment(this);
            submitCrimeFragment.show();
        }
    }
}