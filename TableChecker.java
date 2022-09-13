import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TableChecker extends Thread{
        ConcurrentHashMap<Long, Long> addressMap;


        public TableChecker(ConcurrentHashMap<Long, Long> addressMap) {
            this.addressMap = addressMap;
        }

        @Override
        public void run (){
            boolean printCheck = false;
            while(!Thread.currentThread().isInterrupted()){
                for(ConcurrentHashMap.Entry<Long, Long> set : addressMap.entrySet()){
                    if (System.currentTimeMillis() - set.getValue() > 5000) {
                        printCheck = true;
                        addressMap.remove(set.getKey());
                    }
                }
                if(printCheck){
                    print_map();
                    printCheck = false;
                }
                /*try {
                    sleep(5000);
                    print_map();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }

        public void print_map(){
            for (ConcurrentHashMap.Entry<Long, Long> set : addressMap.entrySet()){
                // Printing all elements of a Map
                System.out.println(set.getKey() + " = "
                        + set.getValue());
            }
        }
    }
