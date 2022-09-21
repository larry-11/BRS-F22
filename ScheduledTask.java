import java.util.TimerTask;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
/**
 * 
 * @author Dhinakaran P.
 */
// Create a class extends with TimerTask
public class ScheduledTask extends TimerTask {

	Date now; // to display current time
    Socket client;
    public ScheduledTask(Socket client) {
        this.client = client;
    }

	// Add your task here
	public void run() {
		try {
			now = new Date(); // initialize date
			System.out.println("Time is :" + now); // Display current time
			SimpleDateFormat format = new SimpleDateFormat("hh-MM-ss");
			String msg = "heartbeat checking...";
			System.out.println("Heart beats send time : " + format.format(new Date()));
			send(TCPService.SERVICE_IP,TCPService.SERVICE_PORT, msg);
			TCPLFD.count += 1;
			if (TCPLFD.count > 3) {
				System.out.println("Server no response");
			}
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}
	}

    private void send(String ip, int port, String msg){
        msg = msg+TCPService.END_CHAR;

        try {

            OutputStream out = client.getOutputStream();
            out.write(msg.getBytes());
        }catch (Exception e){
            // e.printStackTrace();
        }
    }
}