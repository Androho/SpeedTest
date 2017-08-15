package ua.ho.andro.speedtest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

class DownloadImage extends AsyncTask<Object, Object, String> {

    private MainActivity mainActivity;

    public DownloadImage(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(Object... strings) {
        try {
            mainActivity.url = new URL(mainActivity.connectionURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[2 ^ 16];
            InputStream is = mainActivity.url.openStream();
            mainActivity.url.getPath();
            mainActivity.url.getHost();
            long startTime = System.currentTimeMillis();
            int read = is.read(b);
            while (read > -1) {
                baos.write(b, 0, read);
                read = is.read(b);
            }
            long stopTime = System.currentTimeMillis();
            int countInBytes = baos.toByteArray().length;
            double hc = (double) 1048576 / (double) countInBytes;

            double deltaTime = ((stopTime - startTime) * 0.001);
            mainActivity.tt = (hc / deltaTime);
            mainActivity.q = String.valueOf(mainActivity.tt);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mainActivity.q;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        int pr = (int) mainActivity.tt / 10;
        mainActivity.connectionProgress.setProgress(pr);
    }
}
