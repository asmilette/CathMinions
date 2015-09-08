package com.example.davilink.httpconnection;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView response;
    LoadingThread lt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        response = (TextView)findViewById(R.id.response);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lt = new LoadingThread(this);
        lt.start();
    }

    private void sendMessage(String msg) {
        response.setText(msg);
    }

    private class LoadingThread extends Thread {
        MainActivity a;

        LoadingThread(MainActivity a) {
            this.a = a;
        }
        @Override
        public void run() {
            String msg = "";
            try {
                URL url = new URL("http://192.168.57.11:8080/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line);
                    }
                    msg = total.toString();
                }
                finally {
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            a.sendMessage(msg);
        }
    }
}
