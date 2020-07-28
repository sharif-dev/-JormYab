package com.example.jormyab;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

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

public class SubmitCrimeFragment extends Dialog {
    Button submitBtn;
    RadioGroup rg;
    int sendId;
    SharedPreferences setting;
    ProgressBar progressBar;

    public SubmitCrimeFragment(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check_crime);
        rg = findViewById(R.id.radio_crimes);
        setting = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        submitBtn = findViewById(R.id.submit_crime);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkId = rg.getCheckedRadioButtonId();
                if (checkId == -1){
                    Toast.makeText(getContext() , "please select one" , Toast.LENGTH_LONG).show();
                }
                else {
                    findRadioButton(checkId);
                    new add_data().execute();
                     progressBar = new ProgressBar(getContext());
                }
            }
        });
    }
    private void findRadioButton(int checkId){

        switch (checkId){
            case R.id.motor_robbery: sendId = 1; break;
            case R.id.house_robbery: sendId = 2; break;
            case R.id.murdor: sendId = 3; break;
            case R.id.other_crimes: sendId = 4; break;

        }
    }
    public class add_data extends AsyncTask<Void , Void, String>{
        String url;
        String sendIdString;

        String userIdStr;
        public add_data() {
             this.url = "http://172.20.10.3/addData.php";
            this.sendIdString = String.valueOf(sendId) ;
            this.userIdStr = String.valueOf(setting.getInt("user_id",0));
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("showdata" , url);
            Log.d("showdata" , sendIdString);
            Log.d("showdata" , userIdStr);

            submitBtn.setText("wait...");
            submitBtn.setEnabled(false);
        }

        @Override
        protected String doInBackground(Void... voids) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("command","add_data"));
            nameValuePairs.add(new BasicNameValuePair("user_id",userIdStr));
            nameValuePairs.add(new BasicNameValuePair("kind",sendIdString));
            nameValuePairs.add(new BasicNameValuePair("x","0.002"));
            nameValuePairs.add(new BasicNameValuePair("y","1.0213"));

            HttpClient  httpClient = new DefaultHttpClient();
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
                    Log.d("TAAAAG","ferestadam");
                }
               else {
                    Log.d("TAAAAG","naferestadam");
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
            super.onPostExecute(s);
            submitBtn.setEnabled(true);
            submitBtn.setText("submit");
        }
    }
}
