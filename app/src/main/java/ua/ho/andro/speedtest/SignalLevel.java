package ua.ho.andro.speedtest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;

public class SignalLevel extends AsyncTask<Integer, Integer, Integer> {
    private MainActivity mainActivity;
    private Context context;
    private int typeConnection;
    private String connectionType;

    public SignalLevel(MainActivity mainActivity, Context context) {
        this.mainActivity = mainActivity;
        this.context = context;
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        connectionType = info.getTypeName();

        if (connectionType.equalsIgnoreCase("wifi")) {
            typeConnection = 0;
        } else {
            //3g u otro tipo
            typeConnection = 1;
        }

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int numberOfLevels = 6;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);

        return level;
    }

    @Override
    protected void onPostExecute(Integer level) {
        super.onPostExecute(level);
        if (typeConnection == 0) {
            if (level < 1) {
                mainActivity.ivWifi.setImageResource(R.drawable.ic_wifi_1);
            } else if (1 < level && level < 2) {
                mainActivity.ivWifi.setImageResource(R.drawable.ic_wifi_2);
            } else if (2 < level && level < 3) {
                mainActivity.ivWifi.setImageResource(R.drawable.ic_wifi_3);
            } else if (3 < level && level < 5) {
                mainActivity.ivWifi.setImageResource(R.drawable.ic_wifi_4);
            } else if (5 < level && level < 6) {
                mainActivity.ivWifi.setImageResource(R.drawable.ic_wifi_5);
            } else if (6 < level) {
                mainActivity.ivWifi.setImageResource(R.drawable.ic_wifi_6);
            }
        } else {

        }
    }
}
