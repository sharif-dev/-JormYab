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
    SharedPreferences sharedPreferences;

    //    @SuppressLint("ResourceType")
//    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i ;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getInt("user_id", 0) == 0){
            i = new Intent(MainActivity.this, LoginActivity.class);

        }else {
            i = new Intent(MainActivity.this, MenuActivity.class);

        }


        startActivity(i);
        MainActivity.this.finish();


//        Intent service = new Intent(this, AlertDangerService.class);
//        startService(service);


    }


}