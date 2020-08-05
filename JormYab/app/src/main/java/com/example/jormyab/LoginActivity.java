package com.example.jormyab;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    Button submitButton;
    TextInputEditText mobile;
    TextView goToSignUp;
    String mobileStr;
    String name;
    SharedPreferences setting;

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        findViews();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobile.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "mobile must not be empty", Toast.LENGTH_LONG).show();

                } else {
                    if (!mobile.getText().toString().startsWith("09")) {
                        Toast.makeText(getApplicationContext(), "mobile should start with 09", Toast.LENGTH_LONG).show();

                    } else {
                        //TODO
                        mobileStr = mobile.getText().toString();
                        new login().execute();
                    }
                }
            }
        });

    }

    public void findViews() {
        submitButton = findViewById(R.id.login_btn);
        mobile = findViewById(R.id.edit_text_mobile_login);
        goToSignUp = findViewById(R.id.go_to_sign_up_page);


    }

    public void action(View view) {
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
        LoginActivity.this.finish();
    }

    public class login extends AsyncTask<Void, Void, String> {
        ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        String url = "http://192.168.43.28/connection.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("please wait ...");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("command", "login"));
            nameValuePairs.add(new BasicNameValuePair("mobile", mobileStr));
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                final String response = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jo = new JSONObject(response);
                final String res;
                res = jo.getString("result");
                if (res.equals("ok")) {
                    Log.d("checkerOfString", jo.getString("name") + jo.getString("name"));

                    Intent i = new Intent(LoginActivity.this, CodeActivity.class);
                    i.putExtra("mobile", mobileStr);
                    startActivity(i);
                    LoginActivity.this.finish();
                }
                if (res.equals("notexist")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "your phone number isn't exist please login", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.hide();
            pd.dismiss();
            super.onPostExecute(s);
        }


    }
}
