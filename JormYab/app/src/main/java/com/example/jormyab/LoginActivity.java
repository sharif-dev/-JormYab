package com.example.jormyab;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    Button submitButton;
    EditText mobileNumber;
    EditText userName;
    String mobile;
    String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mobile = mobileNumber.getText().toString().trim();
                 name = userName.getText().toString();
                if (name.length()!=0) {
                    if (mobile.length() != 11) {
                        Toast.makeText(getApplicationContext(), "your phone number is not in right format", Toast.LENGTH_LONG).show();
                    } else {
                        if (!mobile.startsWith("09")) {
                            Toast.makeText(getApplicationContext(), "your phone number is not in right format", Toast.LENGTH_LONG).show();

                        } else {
                            //call api
                            new register_user().execute();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"please enter your name",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void  findViews(){
        submitButton = (Button) findViewById(R.id.submit_button);
        mobileNumber = (EditText) findViewById(R.id.mobile_number);
        userName = (EditText) findViewById(R.id.user_name);


    }
    public class register_user extends AsyncTask<Void,Void,String>
    {
        ProgressDialog pd = new ProgressDialog(LoginActivity.this);
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
            nameValuePairs.add(new BasicNameValuePair("name",name));
            nameValuePairs.add(new BasicNameValuePair("mobile",mobile));
            HttpClient  httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                final String response = EntityUtils.toString(httpResponse.getEntity());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
