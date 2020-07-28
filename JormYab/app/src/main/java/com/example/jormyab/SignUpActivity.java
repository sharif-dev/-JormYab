package com.example.jormyab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

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

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText name;
    TextInputEditText lastName;
    TextInputEditText mobile;
    TextInputEditText email;
    TextView goToLogin;
    String nameStr;
    String lastNameStr;
    String mobileStr;
    String emailStr;
    Button signUpBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        findViews();
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext() , "name must not be empty" , Toast.LENGTH_LONG).show();
                }else{
                    if (lastName.getText().toString().length()==0){
                        Toast.makeText(getApplicationContext() , "last name must not be empty" , Toast.LENGTH_LONG).show();

                    }else {
                        if (email.getText().toString().length()==0){
                            Toast.makeText(getApplicationContext() , "email must not be empty" , Toast.LENGTH_LONG).show();

                        }
                        else{
                            if (mobile.getText().toString().length()==0){
                                Toast.makeText(getApplicationContext() , "mobile must not be empty" , Toast.LENGTH_LONG).show();

                            }
                            else {
                                if (!mobile.getText().toString().startsWith("09")){
                                    Toast.makeText(getApplicationContext() , "mobile should start with 09" , Toast.LENGTH_LONG).show();

                                }
                                else {
                                    nameStr = name.getText().toString();
                                    lastNameStr = lastName.getText().toString();
                                    mobileStr = mobile.getText().toString();
                                    emailStr = email.getText().toString();
                                    new register_user().execute();
                                }
                            }
                        }
                    }
                }
            }
        });
//        goToLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    public void findViews(){
        name = findViewById(R.id.edit_text_firstName_signup);
        lastName = findViewById(R.id.edit_text_lastName_signup);
        email = findViewById(R.id.edit_text_email_signup);
        mobile = findViewById(R.id.edit_text_mobile_signup);
        goToLogin = findViewById(R.id.go_to_login_page);
        signUpBtn = findViewById(R.id.sign_up_btn);


    }
    public void performAction(){
//        Intent i = new Intent(SignUpActivity.this , LoginActivity.class);
//        startActivity(i);
//        SignUpActivity.this.finish();
        Log.d("start" , "on click");
    }

    public void performAction(View view) {
        Intent i = new Intent(SignUpActivity.this , LoginActivity.class);
        startActivity(i);
        SignUpActivity.this.finish();
    }

    public class register_user extends AsyncTask<Void,Void,String>
    {
        ProgressDialog pd = new ProgressDialog(SignUpActivity.this);
        String url = "http://172.20.10.3/connection.php";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("please wait ...");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("command","register_user"));
            nameValuePairs.add(new BasicNameValuePair("name",nameStr));
            nameValuePairs.add(new BasicNameValuePair("mobile",mobileStr));
            nameValuePairs.add(new BasicNameValuePair("last_name",lastNameStr));
            nameValuePairs.add(new BasicNameValuePair("email",emailStr));


            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                final String response = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jo = new JSONObject(response);
                final String res;
                res = jo.getString("result");
                if (res.equals("ok")){
                    //activation key send
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();

                        }
                    });
                    Intent i = new Intent(SignUpActivity.this,CodeActivity.class);
                    i.putExtra("mobile",mobileStr);
                    startActivity(i);
                    SignUpActivity.this.finish();
                }
                if (res.equals("exist")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext() , "your phone number is exists in our data base you can login", Toast.LENGTH_LONG).show();
                        }
                    });
                }
//                else {
//                    Toast.makeText(getApplicationContext() , "connection error",Toast.LENGTH_LONG).show();
//                }

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
