package com.example.jormyab;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;

class Client extends AsyncTask<Void, Void, Void> {


    public Client() {

    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Socket socket = new Socket("192.168.43.28", 7800);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
