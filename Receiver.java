import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class Receiver extends Thread{
    InetAddress group;
    int port;
    MulticastSocket socket;
    ConcurrentHashMap<SocketAddress, boolean> addressMap;

    public Receiver(InetAddress group, int port, ConcurrentHashMap<SocketAddress, boolean> addressMap) throws IOException {
        this.group = group;
        this.port = port;
        this.addressMap = addressMap;

        socket = new MulticastSocket(port);
        socket.joinGroup(group);
    }

    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            byte[] buf = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);

            try {
                socket.receive(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            addressMap.put(datagramPacket.getSocketAddress(), true);
        }

    }
}
