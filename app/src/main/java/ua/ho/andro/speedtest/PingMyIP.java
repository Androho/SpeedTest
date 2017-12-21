package ua.ho.andro.speedtest;

import android.os.AsyncTask;

import java.net.*;
import java.io.*;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PingMyIP extends AsyncTask<Object, Object, String> {
    private Elements li = null;
    private MainActivity mainActivity;
    private String title;

    public PingMyIP(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(Object... params) {
        String url = "http://checkip.amazonaws.com";
        URL whatismyip = null;
        try {
            whatismyip = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String ip = null; //you get the IP as a String
        try {
            ip = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ip);
        title=ip;
        return title;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String text;
        if (title != null) {
            text = title;
        } else
            text = "No inet";
        mainActivity.tvYourIp.setText(text);

    }
}