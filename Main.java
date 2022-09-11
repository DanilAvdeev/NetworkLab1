import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        InetAddress group;
        ConcurrentHashMap<SocketAddress, boolean> addressMap = new ConcurrentHashMap<SocketAddress, boolean>();

        try {
            group = InetAddress.getByName(ip);
            //создание сендера
            Sender sender = new Sender(group, port, addressMap);
            sender.start();
            //создание ресивера
            Receiver receiver = new Receiver(group, port, addressMap);
            receiver.start();
            //создание чекера, который выписывает список Ip-адресов

        } catch (IOException e){
            e.printStackTrace();
        }


    }
}
