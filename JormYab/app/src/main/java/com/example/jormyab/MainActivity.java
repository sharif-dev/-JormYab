package com.example.jormyab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    SharedPreferences setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setting = PreferenceManager.getDefaultSharedPreferences(this);
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
        };
    }
}