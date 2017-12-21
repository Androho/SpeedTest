package ua.ho.andro.speedtest;

public class Functional {
    public static void startSpeedTest(MainActivity mainActivity) {
        double res = 0;
        for (int i = 0; i < 10; i++) {
            new DownloadTest(mainActivity).execute();
            res = res + mainActivity.tt;
        }
        int result = (int) res / 10;
        mainActivity.connectionProgress.setProgress(result);
    }
}
