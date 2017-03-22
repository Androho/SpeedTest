package ua.ho.andro.speedtest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;


public class MainActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "TEST";
    private static final String NUMBER_OF_PACKTETS = "10";
    private static final String DEBUG_TAG ="DEBUG" ;
    private String mURL = "http://www.zooclub.ru/skat/img.php?w=700&h=700&img=./attach/12000/12669.jpg";
    private URL url;
    private ProgressBar progressBar;
    private TextView textView, tvRes;
    private Button buttonStart;
    private Button buttonClear;
    private static int progressBarStatus = 0;
    private String q;
    public double tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv_progress_bar);
        progressBar = (ProgressBar) findViewById(R.id.circularProgressBar);
        buttonStart = (Button)findViewById(R.id.btn_start_test);
//        buttonClear =(Button)findViewById(R.id.btn_clear);
        buttonStart.setOnClickListener(this);
//        buttonClear.setOnClickListener(this);
        progressBar.setProgress(progressBarStatus);
        textView.setText(progressBarStatus + " Mb/s");
        tvRes=(TextView)findViewById(R.id.tv_spid_res);
        tvRes.setText(progressBarStatus + " Mb/s");

    }

    public void startSpeedTest() {
        double res = 0;
        for (int i = 0; i < 10; i++) {
            new DownloadImage().execute();
             res = res+tt;
        }
        double resres = res/10;
        String formattedDoubleRes = new DecimalFormat("#0.00").format(resres/10);
        tvRes.setText(formattedDoubleRes);
    }

    public void resetResult() {
        progressBar.setProgress(progressBarStatus);
        textView.setText(progressBarStatus + " Mb/s");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start_test:
                startSpeedTest();
//            case R.id.btn_clear:
//                resetResult();
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
            String formattedDouble = new DecimalFormat("#0.00").format(tt/10);
            textView.setText(formattedDouble + " Mb/s");
            int pr=(int) tt/10;
            progressBar.setProgress(pr);
        }
    }
}


