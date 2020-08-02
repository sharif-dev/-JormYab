package com.example.jormyab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.raycoarana.codeinputview.CodeInputView;
import com.raycoarana.codeinputview.OnCodeCompleteListener;

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

public class CodeActivity extends AppCompatActivity {
//    EditText code;
    Button submit;
    CodeInputView code;
    String codeStr;

    String codeSubmited;
    String mobile;
    SharedPreferences setting;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        mobile = getIntent().getStringExtra("mobile");

        findViews();
        code.addOnCompleteListener(new OnCodeCompleteListener() {
            @Override
            public void onCompleted(String code) {
                //                Toast.makeText(getApplicationContext() , codeStr , Toast.LENGTH_LONG).show();
                new verify_code().execute();

            }
        });


        setting = PreferenceManager.getDefaultSharedPreferences(this);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                codeSubmited = code.getText().toString().trim();
//                if (codeSubmited.length()!=4){
//                    Toast.makeText(getApplicationContext(),"your code is invalid" , Toast.LENGTH_LONG).show();
//                }
//                else {
//                    new verify_code().execute();
//                }
//            }
//        });
    }
    void findViews(){
//        code = (EditText) findViewById(R.id.code);
//        submit = (Button) findViewById(R.id.submit_button);
        code = findViewById(R.id.submit_code);
    }
    public class verify_code extends AsyncTask<Void,Void,String>
    {
        ProgressDialog pd = new ProgressDialog(CodeActivity.this);
        String url = "http://192.168.1.52/connection.php";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(getApplicationContext() , code.getCode() , Toast.LENGTH_LONG).show();
            pd.setMessage("please wait ...");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("command","verify_code"));
            nameValuePairs.add(new BasicNameValuePair("code",code.getCode()));
            nameValuePairs.add(new BasicNameValuePair("mobile",mobile));
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                final String response = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jo = new JSONObject(response);
                final String res;
                res = jo.getString("result");
                if (!res.equals("error")){
                    SharedPreferences.Editor editor = setting.edit();
                    editor.putInt("user_id",jo.getInt("user_id"));
                    editor.commit();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"login ...",Toast.LENGTH_LONG).show();

                        }
                    });
                    Intent i = new Intent(CodeActivity.this,MapsActivity.class);
                    startActivity(i);
                    CodeActivity.this.finish();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();

                    }
                });
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
