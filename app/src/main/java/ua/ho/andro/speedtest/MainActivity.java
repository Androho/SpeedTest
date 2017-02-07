package ua.ho.andro.speedtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;


public class MainActivity extends Activity {
    private static final String TAG = "TEST";
    private String mURL = "http://www.popsci.com/sites/popsci.com/files/styles/large_1x_/public/images/2016/04/earthheader.jpg";
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
            textView.setText(q+" Mb/s");
        }
    }

}


