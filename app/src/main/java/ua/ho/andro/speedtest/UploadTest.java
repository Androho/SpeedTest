package ua.ho.andro.speedtest;

import android.os.AsyncTask;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

public class UploadTest extends AsyncTask<Integer, Integer, Integer> {
    private static final int SOCKET_TIMEOUT = 5000;
    private static final String SPEED_TEST_SERVER_URI_UL = "http://2.testdebit.info/";
    private static final int FILE_SIZE = 10000000;
    private MainActivity mainActivity;
    long v;
    int vv;
    int pp;
    int res;

    public UploadTest(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        final SpeedTestSocket speedTestSocket = new SpeedTestSocket();

        //set timeout for download
        speedTestSocket.setSocketTimeout(SOCKET_TIMEOUT);

        //speedTestSocket.setUploadStorageType(UploadStorageType.FILE_STORAGE);

        // add a listener to wait for speed examples completion and progress
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

            @Override
            public void onCompletion(final SpeedTestReport report) {

                res= report.getTransferRateBit().intValue();
            }

            @Override
            public void onError(final SpeedTestError speedTestError, final String errorMessage) {

            }

            @Override
            public void onProgress(final float percent, final SpeedTestReport downloadReport) {
                long start = downloadReport.getStartTime();
                long stop = downloadReport.getReportTime();
                long time =stop-start;
                long v = FILE_SIZE/time;
                vv = (int) v;
                pp= (int) percent;
                publishProgress(pp);

            }
        });

        speedTestSocket.startUpload(SPEED_TEST_SERVER_URI_UL, FILE_SIZE);

        return res;
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mainActivity.progressBar.setProgress(pp);
        mainActivity.arcProgressOut.setProgress(vv/10);
    }
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        mainActivity.progressBar.setProgress(0);
        mainActivity.arcProgressOut.setProgress(integer/10000);
    }


}
