import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TCPClient {
    private static Socket socketClient;
    public static void main(String[] args) {
        TCPClient TCPClient = new TCPClient();
        SimpleDateFormat format = new SimpleDateFormat("hh-MM-ss");
        Scanner scanner = new Scanner(System.in);
        try {
            socketClient = new Socket(TCPService.SERVICE_IP, TCPService.SERVICE_PORT);
        } catch (IOException e) {
            System.err.println("Initialize socket client failed");
        }
        while(true){
            String msg = scanner.nextLine();
            if("#".equals(msg))
                break;
            System.out.println("send time : " + format.format(new Date()));
            System.out.println(TCPClient.sendAndReceive(TCPService.SERVICE_IP,TCPService.SERVICE_PORT,msg));
            System.out.println("receive time : " + format.format(new Date()));
        }
    }

    private String sendAndReceive(String ip, int port, String msg){
        msg = msg+TCPService.END_CHAR;
        StringBuilder receiveMsg = new StringBuilder();
        try {
            OutputStream out = socketClient.getOutputStream();
            out.write(msg.getBytes());

            InputStream in = socketClient.getInputStream();
            for (int c = in.read(); c != TCPService.END_CHAR; c = in.read()) {
                if(c==-1)
                    break;
                receiveMsg.append((char)c);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return receiveMsg.toString();
    }
}