package com.example.jormyab;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Client2 extends AsyncTask<String, Void, Boolean> {

    Socket socket;
    Scanner scanner;
    Formatter formatter;

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
//            this.socket = new Socket("192.168.43.28", 7800);  // for phone
            this.socket = new Socket("192.168.1.35", 7800);  //for zyxel
            this.formatter = new Formatter(socket.getOutputStream());
            this.scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }

        String command = "location " + strings[0] + "\n";
        System.out.println(command);
        this.formatter.format(command);
        this.formatter.flush();
        String response = scanner.nextLine();
        System.out.println(response);
        return response.equals("1");
    }
}
