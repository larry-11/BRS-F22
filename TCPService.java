import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPService {
    public static final String SERVICE_IP = "127.0.0.1";

    public static final int SERVICE_PORT = 10101;

    public static final char END_CHAR = '#';

    private int MessageNumber = 0;

    public static void main(String[] args) {
        TCPService service = new TCPService();
        service.startService();
    }

    private void startService(){
        try {
            InetAddress address = InetAddress.getByName(SERVICE_IP);
            Socket connect = null;
            ExecutorService pool = Executors.newFixedThreadPool(5);
            try (ServerSocket service = new ServerSocket(SERVICE_PORT,5,address)){
                while(true){
                    connect = service.accept();
                    ServiceTask serviceTask = new ServiceTask(connect);
                    pool.execute(serviceTask);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(connect!=null)
                    connect.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ServiceTask implements Runnable{
        private Socket socket;

        ServiceTask(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            while(true) {
                try {
                    StringBuilder receiveMsg = new StringBuilder();
                    InputStream in = socket.getInputStream();
                    for (int c = in.read(); c != END_CHAR; c = in.read()) {
                        if(c ==-1)
                            break;
                        receiveMsg.append((char)c);
                    }
                    System.out.println("Received message: " + receiveMsg.toString());
                    MessageNumber += 1;
                    String response = "ACKed: " + String.valueOf(MessageNumber) + " " + receiveMsg.toString() + END_CHAR;
                    OutputStream out = socket.getOutputStream();
                    out.write(response.getBytes());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            // }finally {
            //    if(socket!=null)
            //        try {
            //            socket.close();
            //        } catch (IOException e) {
            //            e.printStackTrace();
            //        }
            // }
        }
    }
}
