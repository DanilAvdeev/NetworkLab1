import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class Sender extends Thread{
    InetAddress group;
    int port;
    MulticastSocket socket;
    ConcurrentHashMap<Long, Long> addressMap;
    DatagramPacket datagramPacket;
    String message;
    Long PID;

    public Sender(InetAddress group, int port, ConcurrentHashMap<Long, Long> addressMap) throws IOException {
        this.group = group;
        this.port = port;
        this.addressMap = addressMap;

        socket = new MulticastSocket(port);
        //socket.setInterface(InetAddress.getByName("localhost"));  //
        socket.joinGroup(new InetSocketAddress(group, port), NetworkInterface.getByInetAddress(group)); //
        //socket.joinGroup(group);
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName(); // format: "pid@hostname"
        PID = Long.parseLong(name.substring(0, name.indexOf('@')));
        message = PID + ".";
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
