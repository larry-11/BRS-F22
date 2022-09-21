import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class TCPLFD {
    
    public static int count = 0;
    public static Socket client;

    // Initialization
    public TCPLFD() {
        Timer time = new Timer(); // Instantiate Timer Object
        try {
            client = new Socket(TCPService.SERVICE_IP,TCPService.SERVICE_PORT);
            ScheduledTask st = new ScheduledTask(client); // Instantiate SheduledTask class
            time.schedule(st, 0, 5000); // Create Repetitively task for every 1 secs
        } catch (Exception e) {
            //System.out.println("catch th"e);
        }

    }
    

    public static void main(String[] args) {
        TCPLFD lfd = new TCPLFD();

        while (true) {
            StringBuilder receiveMsg = new StringBuilder();
            try {
               InputStream in = TCPLFD.client.getInputStream();
               for (int c = in.read(); c != TCPService.END_CHAR; c = in.read()) {
                if(c==-1)
                    break;
                receiveMsg.append((char)c);
                }
                if (receiveMsg != null && receiveMsg.length() != 0) {
                    System.out.println(receiveMsg.toString() + "length with: " + receiveMsg.length());
                    count = 0;
                }

            } catch (Exception e) {
                // System.out.println(e.getMessage());
            }
            
		}
    }
}