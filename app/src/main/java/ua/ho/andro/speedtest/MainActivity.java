package ua.ho.andro.speedtest;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.net.URL;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    public String connectionURL = "https://sonikelf.ru/attach/img/1302869217-clip-64kb.jpg";
    public URL url;
    public ArcProgress connectionProgress;
    private Button buttonStart;
    private static int progressBarStatus = 0;
    public String q;
    public double tt;
    private AdView mAdView;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionProgress = (ArcProgress) findViewById(R.id.arc_progress);
        buttonStart = (Button) findViewById(R.id.btn_start_test);
        mAdView = (AdView) findViewById(R.id.adView);

        connectionProgress.setProgress(progressBarStatus);
        buttonStart.setOnClickListener(this);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Context context = getApplicationContext();
                wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

// Level of a Scan Result
                List<ScanResult> wifiList = wifiManager.getScanResults();
                for (ScanResult scanResult : wifiList) {
                    int level = WifiManager.calculateSignalLevel(scanResult.level, 5);
                    System.out.println("Level is " + level + " out of 5");
                }

// Level of current connection
                int rssi = wifiManager.getConnectionInfo().getRssi();
                int level = WifiManager.calculateSignalLevel(rssi, 5);
                System.out.println("Level is " + level + " out of 5");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_test:
                Functional.startSpeedTest(this);
        }
    }
}


