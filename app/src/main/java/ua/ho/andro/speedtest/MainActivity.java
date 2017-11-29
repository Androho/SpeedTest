package ua.ho.andro.speedtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.net.URL;


public class MainActivity extends Activity implements View.OnClickListener {

    public String mURL = "https://sonikelf.ru/attach/img/1302869217-clip-64kb.jpg";
    public URL url;
    public ArcProgress arcProgress;
    private Button buttonStart;
    private static int progressBarStatus = 0;
    public String q;
    public double tt;
    private AdView mAdView;
    public TextView tvYourIp;
    public ProgressBar progressBar;
    public ImageView ivWifi;
    public ProgressBar connectionProgress;
    private final static int SOCKET_TIMEOUT = 5000;
    private final static String SPEED_TEST_SERVER_URI_DL = "http://2.testdebit.info/fichiers/10Mo.dat";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arcProgress = (ArcProgress) findViewById(R.id.arc_progress);
        arcProgress.setProgress(progressBarStatus);
        buttonStart = (Button) findViewById(R.id.btn_start_test);
        buttonStart.setOnClickListener(this);
        tvYourIp = (TextView) findViewById(R.id.tv_your_ip_dat);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ivWifi = (ImageView) findViewById(R.id.iv_signal_rang);
        ivWifi.setImageResource(R.drawable.ic_wifi_1);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //new PingIP(this).execute();

    }


    public void startSpeedTest() {
        double res = 0;
        for (int i = 0; i < 10; i++) {
            new DownloadImage(this).execute();
            res = res + tt;
        }
        int resres = (int) res / 10;
        arcProgress.setProgress(resres);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_test:
                new DownloadImage(this).execute();
                //startSpeedTest();
        }
    }
}


