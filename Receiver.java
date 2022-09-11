import java.io.IOException;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class Receiver extends Thread{
    InetAddress group;
    int port;
    MulticastSocket socket;
    ConcurrentHashMap<SocketAddress, Boolean> addressMap;

    public Receiver(InetAddress group, int port, ConcurrentHashMap<SocketAddress, Boolean> addressMap) throws IOException {
        this.group = group;
        this.port = port;
        this.addressMap = addressMap;

        socket = new MulticastSocket(port);
        socket.joinGroup(group);
        //socket.joinGroup(new InetSocketAddress(group, port), NetworkInterface.getByInetAddress(group));
    }

    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            byte[] buf = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);

            try {
                socket.receive(datagramPacket);
                String receivedData = new String(datagramPacket.getData());
                System.out.println("Received: " + receivedData);
                addressMap.put(datagramPacket.getSocketAddress(), true);
                System.out.println("Socket address: " + datagramPacket.getSocketAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
