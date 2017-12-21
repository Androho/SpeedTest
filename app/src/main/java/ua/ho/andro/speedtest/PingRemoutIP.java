package ua.ho.andro.speedtest;

import android.os.AsyncTask;

import java.net.*;

public class PingRemoutIP extends AsyncTask<Object, Object, String> {

    private MainActivity mainActivity;
    private String title;

    public PingRemoutIP(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(Object... objects) {
        try {
            InetAddress ip = InetAddress.getByName("www.google.com");
            title= ip.getHostAddress();
        } catch (Exception e) {
            System.out.println(e);
        }

        return title;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String text;
        if (title != null) {
            text = title;
        } else
            text = "No inet adress";
        mainActivity.tvRemoutIp.setText(text);

    }
}
