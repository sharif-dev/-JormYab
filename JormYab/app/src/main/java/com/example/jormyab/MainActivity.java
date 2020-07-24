package com.example.jormyab;

import androidx.appcompat.app.AppCompatActivity;

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
        if (setting.getInt("user_id"  , 0) == 0 ){
            //TODO
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
            MainActivity.this.finish();
        }
        else {
            //TODO
        }
    }
}