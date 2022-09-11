import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class Sender {
    InetAddress group;
    int port;
    MulticastSocket socket;
    ConcurrentHashMap<SocketAddress, long> addressMap;
    DatagramPacket datagram;

    public Sender(InetAddress group, int port, ConcurrentHashMap<SocketAddress, long> addressMap) throws IOException {
        this.group = group;
        this.port = port;
        this.addressMap = addressMap;

        socket = new MulticastSocket();
//
        //datagram = new DatagramPacket();
    }

}
