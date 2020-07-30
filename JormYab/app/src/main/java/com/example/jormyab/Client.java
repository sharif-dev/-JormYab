package com.example.jormyab;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;

import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.TreeMap;

class Client extends AsyncTask<Void, Void, Void> {

    Socket socket;
    Scanner scanner;
    Formatter formatter;
    String command;
    String response;
    GoogleMap mMap;
    PolygonOptions[] polygonOptions = new PolygonOptions[16];
    ArrayList<CircleOptions> circleOptions = new ArrayList<>();

    public GoogleMap getMap() {
        return mMap;
    }

    public void setMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public Client() {

    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Void doInBackground(Void... voids) {
        try {
//            this.socket = new Socket("192.168.43.28", 7800);  // for phone
            this.socket = new Socket("192.168.1.35", 7800);  //for zyxel
            this.formatter = new Formatter(socket.getOutputStream());
            this.scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        formatter.format(command);
        formatter.flush();
        response = scanner.nextLine();
        System.out.println(command);
        System.out.println(response);

        String[] subs;
        String[] v;
        String[] a = response.split("=");
        switch (a[0]) {
            case "1":
                subs = a[1].split("c");
                polygonOptions[0] = new PolygonOptions();
                v = subs[0].split("-");
                for (int i = 0; i < 4; i++) {
                    polygonOptions[0].add(new LatLng(Double.parseDouble(v[i].split(" ")[1]), Double.parseDouble(v[i].split(" ")[0])));
                }
                polygonOptions[0].strokeColor(Color.TRANSPARENT);
                polygonOptions[0].fillColor(findColor(subs[1]));

                break;
            case "2":
            case "3":
                String[] squares = a[1].split(",");
                for (int i = 0; i < squares.length; i++) {
                    subs = squares[i].split("c");
                    polygonOptions[i] = new PolygonOptions();
                    v = subs[0].split("-");
                    for (int j = 0; j < 4; j++) {
                        polygonOptions[i].add(new LatLng(Double.parseDouble(v[j].split(" ")[1]), Double.parseDouble(v[j].split(" ")[0])));
                    }
                    polygonOptions[i].strokeColor(Color.BLACK);
                    polygonOptions[i].fillColor(findColor(subs[1]));

                }
                break;
            case "4":
                System.out.println(response);
                if (a.length > 1) {
                    String[] points = a[1].split(",");
                    for (String point : points) {
                        subs = point.split("c");
                        CircleOptions circleOption = new CircleOptions();
                        circleOption.center(new LatLng(Double.parseDouble(subs[0].split(" ")[1]), Double.parseDouble(subs[0].split(" ")[0])));
                        circleOption.radius(25);
                        circleOption.fillColor(Color.RED);
                        circleOption.strokeColor(Color.TRANSPARENT);
                        circleOptions.add(circleOption);
                    }
                }
                break;
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int findColor(String s){
        System.out.println(s);
        int c = Integer.parseInt(s);
        int color = 0;
        switch (c){
            case 0:
                color = Color.argb((float) 0.3, Color.valueOf(Color.GREEN).red(), Color.valueOf(Color.GREEN).green(), Color.valueOf(Color.GREEN).blue());
                break;
            case 1:
                color = Color.argb((float) 0.3, Color.valueOf(Color.YELLOW).red(), Color.valueOf(Color.YELLOW).green(), Color.valueOf(Color.YELLOW).blue());
                break;
            case 2:
                color = Color.argb((float) 0.3, Color.valueOf(Color.RED).red(), Color.valueOf(Color.WHITE).green(), Color.valueOf(Color.WHITE).blue());
                break;
            case  3:
                color = Color.argb((float) 0.3, Color.valueOf(Color.RED).red(), Color.valueOf(Color.BLACK).green(), Color.valueOf(Color.BLACK).blue());
                break;
            case 4:
                color = Color.argb((float) 0.5, Color.valueOf(Color.BLACK).red(), Color.valueOf(Color.BLACK).green(), Color.valueOf(Color.BLACK).blue());
                break;

        }

        return color;
    }



    @Override
    protected void onPostExecute(Void aVoid) {
        for (int i = 0; i < polygonOptions.length; i++) {
            if (polygonOptions[i] != null) {
                mMap.addPolygon(polygonOptions[i]);
            }
        }

        for (int i=0 ; i < circleOptions.size() ; i++){
            mMap.addCircle(circleOptions.get(i));
        }

        super.onPostExecute(aVoid);
    }
}
