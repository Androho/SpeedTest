package ua.ho.andro.speedtest;

import android.os.AsyncTask;
import android.util.Log;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

class DownloadTest extends AsyncTask<Integer, Integer, Integer> {

    private MainActivity mainActivity;
    private long v;
    private int vv;
    private int pp;
    private int res;

    public DownloadTest(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected Integer doInBackground(Integer... params) {

        SpeedTestSocket speedTestSocket = new SpeedTestSocket();

        // add a listener to wait for speedtest completion and progress
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

            @Override
            public void onCompletion(SpeedTestReport report) {
                // called when download/upload is finished
                res= report.getTransferRateBit().intValue();
            }

            @Override
            public void onError(SpeedTestError speedTestError, String errorMessage) {
                // called when a download/upload error occur
                Log.v("speedtest", "Error : " + speedTestError.name());
            }

            @Override
            public void onProgress(float percent, SpeedTestReport report) {
                // called to notify download/upload progress
                    long start = report.getStartTime();
                    long stop = report.getReportTime();
                    long time =stop-start;
                    long size = report.getTemporaryPacketSize();
                v = size/time;
                 vv = (int) v;
                pp= (int) percent;
                publishProgress(pp);

            }
        });

        speedTestSocket.startDownload("http://2.testdebit.info/fichiers/1Mo.dat");
        return res;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //mainActivity.arcProgress.setProgress(values[0]);
       mainActivity.progressBar.setProgress(pp);
        mainActivity.arcProgress.setProgress(vv/10);
        if(values[0]<10){
            mainActivity.ivWifi.setImageResource(R.drawable.ic_wifi_1);
        }else if (10<values[0] && values[0]<20){
            mainActivity.ivWifi.setImageResource(R.drawable.ic_wifi_2);
        }else if (20<values[0] && values[0]<30){
            mainActivity.ivWifi.setImageResource(R.drawable.ic_wifi_3);
        }else if (30<values[0] && values[0]<50){
            mainActivity.ivWifi.setImageResource(R.drawable.ic_wifi_4);
        }else if (50<values[0] && values[0]<70){
            mainActivity.ivWifi.setImageResource(R.drawable.ic_wifi_5);
        }else if (70<values[0] && values[0]<100){
            mainActivity.ivWifi.setImageResource(R.drawable.ic_wifi_6);
        }
        if (values[0]==100){
            mainActivity.onDownloadFinished();
        }
    }

    @Override
    protected void onPostExecute(Integer s) {
        super.onPostExecute(s);
        mainActivity.arcProgress.setProgress(s/10000);
        mainActivity.progressBar.setProgress(0);
    }
}