import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class TableChecker extends Thread{
        ConcurrentHashMap<SocketAddress, Long> addressMap;


        public TableChecker(ConcurrentHashMap<SocketAddress, Long> addressMap) {
            this.addressMap = addressMap;
        }

        @Override
        public void run (){
            boolean printCheck = false;
            while(!Thread.currentThread().isInterrupted()){
                for(ConcurrentHashMap.Entry<SocketAddress, Long> set : addressMap.entrySet()){
                    if (System.currentTimeMillis() - set.getValue() > 3000) {
                        printCheck = true;
                        addressMap.remove(set.getKey());
                    }
                }
                if(printCheck){
                    print_map();
                    printCheck = false;
                }
            }
        }

        public void print_map(){
            System.out.println("Working addresses: ");
            for (ConcurrentHashMap.Entry<SocketAddress, Long> set : addressMap.entrySet()){
                // Printing all elements of a Map
                System.out.println("Socket address: " + set.getKey());
            }
            System.out.println("--------------------");
        }
    }
