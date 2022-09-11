import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class Sender extends Thread{
    InetAddress group;
    int port;
    MulticastSocket socket;
    ConcurrentHashMap<SocketAddress, Boolean> addressMap;
    DatagramPacket datagramPacket;
    String message = "Hi there!";

    public Sender(InetAddress group, int port, ConcurrentHashMap<SocketAddress, Boolean> addressMap) throws IOException {
        this.group = group;
        this.port = port;
        this.addressMap = addressMap;

        socket = new MulticastSocket();
        datagramPacket = new DatagramPacket(message.getBytes(), message.length(), group, port);
    }

    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                socket.send(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //попробовать без слипа
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
