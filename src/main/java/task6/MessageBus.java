package task6;


import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Maxim on 03.02.2017.
 */
public class MessageBus {

    public static void main(String args[]) {
        Queue sharedQueue = new LinkedList<>();
        int size = 4;
        Thread producer = new Thread(new Producer(sharedQueue, size), "Producer");
        Thread consumer = new Thread(new Consumer(sharedQueue, size), "Consumer");
        producer.start();
        consumer.start();
    }
}


