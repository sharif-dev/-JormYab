package com.example.jormyab;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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

public class SubmitCrime extends Fragment {
    private Button location;
    private Button submit;
    private TextView welcome;
    private Spinner hour;
    private Spinner day;
    private Spinner month;
    private Spinner year;
    private Spinner crime;
    private TextInputEditText other;
    private String otherStr;
    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);


    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.submit_crime, container, false);


    }
    /*public class register_user extends AsyncTask<Void,Void,String>
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
    }*/
    public void findViews(View view) {
        Log.d("taatat", " i am in find view");
        hour= view.findViewById(R.id.spinner2);
        day = view.findViewById(R.id.spinner3);
        month = view.findViewById(R.id.spinner4);
        year = view.findViewById(R.id.spinner5);
        crime =view.findViewById(R.id.spinner1);
        submit = view.findViewById(R.id.submit_btn_crrime);
        location = view.findViewById(R.id.location_btn_crime);
        welcome = view.findViewById(R.id.welcome_crime);
        other = view.findViewById(R.id.text_input_layout_other);
    }
}