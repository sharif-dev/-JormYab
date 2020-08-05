package com.example.jormyab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    ImageView profile;
    ImageView map;
    ImageView reportCrime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        findViews();
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this , ProfileActivity.class);
                startActivity(i);

            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this , MapsActivity.class);
                startActivity(i);
            }
        });

        reportCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MenuActivity.this, SubmitCrime.class);
                startActivity(i);
            }
        });

    }




    private void findViews(){
        profile = findViewById(R.id.profile_image);
        map = findViewById(R.id.map_image);
        reportCrime = findViewById(R.id.report_image);
    }
}
