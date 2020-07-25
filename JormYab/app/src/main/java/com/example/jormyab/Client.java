package com.example.jormyab;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.net.Socket;
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
            this.socket = new Socket("192.168.43.28", 7800);
            this.formatter = new Formatter(socket.getOutputStream());
            this.scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        formatter.format(command);
        formatter.flush();
        response = scanner.nextLine();
        System.out.println(command);
        System.out.println(response);

        String[] subs;
        String[] v;
        String[] a = response.split("=");
        System.out.println("!!!!!!!!!!!!" + a[1]);
        switch (a[0]){
            case "1":
                subs = a[1].split("c");
                polygonOptions[0] = new PolygonOptions();
                v = subs[0].split("-");
                for (int i = 0 ; i < 4 ; i++){
                    polygonOptions[0].add(new LatLng(Double.parseDouble(v[i].split(" ")[1]), Double.parseDouble(v[i].split(" ")[0])));
                }
                polygonOptions[0].strokeColor(Color.TRANSPARENT);
                polygonOptions[0].fillColor(Color.argb((float) 0.5, Color.valueOf(Color.RED).red(), Color.valueOf(Color.RED).green(), Color.valueOf(Color.RED).blue()));

                break;
            case "2":
            case "3":
                String[] squares = a[1].split(",");
                for (int i = 0; i < squares.length; i++) {
                    subs = squares[i].split("c");
                    polygonOptions[i] = new PolygonOptions();
                    v = subs[0].split("-");
                    for (int j = 0 ; j < 4 ; j++){
                        polygonOptions[i].add(new LatLng(Double.parseDouble(v[j].split(" ")[1]), Double.parseDouble(v[j].split(" ")[0])));
                    }
                    polygonOptions[i].strokeColor(Color.BLACK);
                    polygonOptions[i].fillColor(Color.argb((float) 0.3, Color.valueOf(Color.BLUE).red(), Color.valueOf(Color.BLUE).green(), Color.valueOf(Color.BLUE).blue()));

                }
                break;
            case "4":
                System.out.println(response);
                String[] points = a[1].split(",");
                for (String point : points) {
                    subs = point.split("c");
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

    @Override
    protected void onPostExecute(Void aVoid) {
        for (int i = 0; i < polygonOptions.length; i++) {
            if (polygonOptions[i] != null){
                mMap.addPolygon(polygonOptions[i]);
            }
        }

        super.onPostExecute(aVoid);
    }
}
