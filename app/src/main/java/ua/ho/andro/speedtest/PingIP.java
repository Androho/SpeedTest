package ua.ho.andro.speedtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static android.R.attr.host;
import static android.R.attr.port;

public class PingIP {

    public static void runSystemCommand(String command) {

        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            String s = "";
            // reading output stream of the command
            while ((s = inputStream.readLine()) != null) {
                System.out.println(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Ping Poller Starts...");

        String ipAddress = "triadalawyers.com.ua";

        try {

            Socket socket = new Socket(ipAddress,80);

            InputStream in = socket.getInputStream();

            OutputStream out = socket.getOutputStream();

            byte[] buffer = new byte[40];

            System.out.println("Connected.");

            for (int i = 0; i < 1000; i++) {

                out.write(buffer);

                System.out.print("+");

                while (in.available() < 39) {

                    Thread.currentThread().sleep(1);

                }

                in.read(buffer);

                System.out.print("-");

            }

            out.write((byte)32);

            System.out.println("\nDone.");

        } catch (IOException e) {

            e.printStackTrace();

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}

//        String ip = "google.com";
//        runSystemCommand("ping " + ip);
//
//
//    }
//}
