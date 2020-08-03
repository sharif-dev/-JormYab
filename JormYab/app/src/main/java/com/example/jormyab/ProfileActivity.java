package com.example.jormyab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.gson.Gson;

public class ProfileActivity extends AppCompatActivity {
    TextView name, lastName ,  email, phoneNumber;
    SharedPreferences setting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_fragment);
        findViews();
        setting = PreferenceManager.getDefaultSharedPreferences(this);

        name.setText(setting.getString("name",""));
        lastName.setText(setting.getString("last_name" ,""));
        email.setText(setting.getString("email" , ""));
        phoneNumber.setText(setting.getString("mobile",""));
        Toast.makeText(getApplicationContext() , setting.getString("user", null) , Toast.LENGTH_LONG).show();


    }

    private void findViews(){
        name = findViewById(R.id.name_of_user_profile);
        lastName = findViewById(R.id.last_name_of_user_profile);
        email = findViewById(R.id.email_profile);
        phoneNumber = findViewById(R.id.phone_number_profile);
    }
}
