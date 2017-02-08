package ua.ho.andro.speedtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Enumeration;


public class MainActivity extends Activity {
    private static final String TAG = "TEST";
    private static final String NUMBER_OF_PACKTETS = "10";
    private String mURL = "http://luxfon.com/images/201306/luxfon.com_21682.jpg";
    private URL url;
    private TextView textView;
    private String q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.connection_class);
        new DownloadImage().execute();

    }
    public String getLocalIpAddress(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.i(TAG, "***** IP="+ ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, ex.toString());
        }
        return null;
    }
    private class DownloadImage extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... strings) {
            try {
                url = new URL(mURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap mIcon11 = null;
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream is = url.openStream();
                long em1 = System.currentTimeMillis();
                byte[] b = new byte[2 ^ 16];
                int read = is.read(b);
                while (read > -1) {
                    baos.write(b, 0, read);
                    read = is.read(b);
                }
                int countInBytes = baos.toByteArray().length;
                int hc = 1048576 / countInBytes;
                long em2 = System.currentTimeMillis();
                double em3 = ((em2 - em1) * 0.001);
                double tt = (hc / em3);
                String spid = Integer.toString(hc) + "/" + Double.toString(em3);
                String t = spid;
                String ssss = Double.toString(tt);
                String formattedDouble = new DecimalFormat("#0.00").format(tt);
                q = formattedDouble;
                mIcon11 = BitmapFactory.decodeStream(is);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return q;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(q+" Mb/s" + "\n Yor IP: "+getLocalIpAddress());
            getLatency("209.185.108.134");
        }
    }
    public double getLatency(String ipAddress){
        String pingCommand = "/system/bin/ping -c " + NUMBER_OF_PACKTETS + " " + ipAddress;
        String inputLine = "";
        double avgRtt = 0;

        try {
            // execute the command on the environment interface
            Process process = Runtime.getRuntime().exec(pingCommand);
            // gets the input stream to get the output of the executed command
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            inputLine = bufferedReader.readLine();
            while ((inputLine != null)) {
                if (inputLine.length() > 0 && inputLine.contains("avg")) {  // when we get to the last line of executed ping command
                    break;
                }
                inputLine = bufferedReader.readLine();
            }
        }
        catch (IOException e){
            Log.v(TAG, "getLatency: EXCEPTION");
            e.printStackTrace();
        }

        // Extracting the average round trip time from the inputLine string
        String afterEqual = inputLine.substring(inputLine.indexOf("="), inputLine.length()).trim();
        String afterFirstSlash = afterEqual.substring(afterEqual.indexOf('/') + 1, afterEqual.length()).trim();
        String strAvgRtt = afterFirstSlash.substring(0, afterFirstSlash.indexOf('/'));
        avgRtt = Double.valueOf(strAvgRtt);

        return avgRtt;
    }
}


