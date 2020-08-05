package com.example.jormyab;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
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
import java.util.Calendar;
import java.util.Date;

public class SubmitCrime extends Activity implements AdapterView.OnItemSelectedListener {
    private Button location;
    private Button submit;
    private TextView welcome;
    private TextView longitude;
    private TextView latitude;

    private Spinner crime;
    private String otherStr;
    private int hour1;
    private int day1;
    private int month1;
    private int year1;
    private String crimeStr;
    private Date date;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListner;
    private String dateStr = "";
    private int crimeInt;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_crime);
        findViews();
        viewProcess();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }


    public void viewProcess() {

        ArrayAdapter<CharSequence> adaptor = ArrayAdapter.createFromResource(this, R.array.crimes, android.R.layout.simple_spinner_item);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        crime.setAdapter(adaptor);
        crime.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SubmitCrime.this, MapForReportCrimes.class);
                startActivity(i);
            }
        });

        crimeStr = crime.getSelectedItem().toString();
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                year1 = cal.get(Calendar.YEAR);
                month1 = cal.get(Calendar.MONTH);
                day1 = cal.get(Calendar.DAY_OF_MONTH);
                //hour1 = cal.get(Calendar.HOUR_OF_DAY);
                DatePickerDialog dialog = new DatePickerDialog(SubmitCrime.this, android.R.style.Theme_Dialog, mDateSetListner, year1, month1, day1);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });
        date = new Date();
        mDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setYear(year);
                date.setMonth(month);
                date.setDate(dayOfMonth);
                month = month + 1;
                dateStr = year + "/" + month + "/" + dayOfMonth;
                mDisplayDate.setText(dateStr);

            }
        };
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateStr.length() == 0) {
                    Toast.makeText(SubmitCrime.this, "Date must not be empty", Toast.LENGTH_LONG).show();
                } else {
                    new addDataToDAtaBase().execute();

                }
            }


        });


    }

    @Override
    protected void onRestart() {
        longitude.setText(sharedPreferences.getString("longitude", ""));
        latitude.setText(sharedPreferences.getString("latitude", ""));
        super.onRestart();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        crimeStr = (String) parent.getItemAtPosition(position);
        if (crimeStr.equals("motor robbery")) {
            crimeInt = 1;
        } else if (crimeStr.equals(("rubbery from house"))) {
            crimeInt = 2;
        } else if (crimeStr.equals(("murder"))) {
            crimeInt = 3;
        } else {
            crimeInt = 4;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class addDataToDAtaBase extends AsyncTask<Void, Void, String> {
        ProgressDialog pd = new ProgressDialog(SubmitCrime.this);
        String url = "http://192.168.43.28/addData.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("please wait ...");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("command", "add_data"));
            nameValuePairs.add(new BasicNameValuePair("user_id", String.valueOf(sharedPreferences.getInt("user_id", 0))));
            nameValuePairs.add(new BasicNameValuePair("longitude", longitude.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("latitude", latitude.getText().toString()));
//            nameValuePairs.add(new BasicNameValuePair("date", date.toString()));
            nameValuePairs.add(new BasicNameValuePair("kind", String.valueOf(crimeInt)));
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
                    //activation key send
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SubmitCrime.this, "violence reported", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i = new Intent(SubmitCrime.this, MenuActivity.class);
                    startActivity(i);
                    finish();
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

    }

    public void findViews() {
        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);

        crime = findViewById(R.id.spinner1);
        mDisplayDate = findViewById(R.id.select_date);
        submit = findViewById(R.id.submit_btn_crrime);
        location = findViewById(R.id.location_btn_crime);
        welcome = findViewById(R.id.welcome_crime);

    }
}