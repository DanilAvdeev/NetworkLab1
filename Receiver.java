import java.io.IOException;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class Receiver extends Thread{
    InetAddress group;
    int port;
    MulticastSocket socket;
    ConcurrentHashMap<SocketAddress, Long> addressMap;

    public Receiver(InetAddress group, int port, ConcurrentHashMap<SocketAddress, Long> addressMap) throws IOException {
        this.group = group;
        this.port = port;
        this.addressMap = addressMap;

        socket = new MulticastSocket(port);
        //socket.joinGroup(group);
        //socket.setInterface(InetAddress.getByName("192.168.1.68"));
        //socket.setNetworkInterface(NetworkInterface.getByName("192.168.137.1"));
        socket.joinGroup(new InetSocketAddress(group, port), NetworkInterface.getByInetAddress(group));
    }

    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            byte[] buf = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);

            try {
                socket.receive(datagramPacket);
                String receivedData = new String(datagramPacket.getData());
//                System.out.println("Received: " + receivedData);
                long check = Long.parseLong(receivedData.substring(0, receivedData.indexOf(".")));
                System.out.print("PID: " + check + ", ");
                //addressMap.put(check, System.currentTimeMillis());
                addressMap.put(datagramPacket.getSocketAddress(), System.currentTimeMillis());
                System.out.println("Socket address: " + datagramPacket.getSocketAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
