package ua.ho.andro.speedtest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;


public class MainActivity extends Activity implements View.OnClickListener{

    private String mURL = "https://sonikelf.ru/attach/img/1302869217-clip-64kb.jpg";
    private URL url;
    private ArcProgress arcProgress;
    private Button buttonStart;
    private static int progressBarStatus = 0;
    private String q;
    public double tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arcProgress = (ArcProgress)findViewById(R.id.arc_progress);
        arcProgress.setProgress(progressBarStatus);
        buttonStart = (Button)findViewById(R.id.btn_start_test);
    }

    public void startSpeedTest() {
        double res = 0;
        for (int i = 0; i < 10; i++) {
            new DownloadImage().execute();
             res = res+tt;
        }
        int resres = (int) res/10;
        arcProgress.setProgress(resres);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start_test:
                startSpeedTest();
        }
    }


    private class DownloadImage extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... strings) {
            try {
                url = new URL(mURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] b = new byte[2 ^ 16];
                InputStream is = url.openStream();
                long startTime = System.currentTimeMillis();
                int read = is.read(b);
                while (read > -1) {
                    baos.write(b, 0, read);
                    read = is.read(b);
                }
                long stopTime = System.currentTimeMillis();
                int countInBytes = baos.toByteArray().length;
                double hc = (double) 1048576 / (double) countInBytes;

                double deltaTime = ((stopTime - startTime)*0.001 );
                tt = (hc / deltaTime);
                q = String.valueOf(tt);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return q;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            int pr=(int) tt/10;
            arcProgress.setProgress(pr);
        }
    }
}


