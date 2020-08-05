package com.example.jormyab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.gson.Gson;

public class ProfileActivity extends AppCompatActivity {
    TextView name, lastName, email, phoneNumber, fullName;
    Button exit;
    SharedPreferences setting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_fragment);
        findViews();
        setting = PreferenceManager.getDefaultSharedPreferences(this);

        name.setText(setting.getString("name", ""));
        lastName.setText(setting.getString("last_name", ""));
        email.setText(setting.getString("email", ""));
        phoneNumber.setText(setting.getString("mobile", ""));
        fullName.setText(new StringBuilder().append(name.getText().toString()).append(" ").append(lastName.getText().toString()));

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= setting.edit();
                editor.putInt("user_id", 0);
                editor.commit();
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();
            }
        });


    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void findViews() {
        name = findViewById(R.id.name_of_user_profile);
        lastName = findViewById(R.id.last_name_of_user_profile);
        email = findViewById(R.id.email_profile);
        phoneNumber = findViewById(R.id.phone_number_profile);
        fullName = findViewById(R.id.tv_name);
        exit = findViewById(R.id.exit);
    }
}
