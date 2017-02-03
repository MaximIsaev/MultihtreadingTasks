package task6;

import java.util.Queue;

/**
 * Created by Maxim on 03.02.2017.
 */
public class Producer implements Runnable {

    private final Queue sharedQueue;
    private final int SIZE;

    public Producer(Queue sharedQueue, int size) {
        this.sharedQueue = sharedQueue;
        this.SIZE = size;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            //If this is here, so program works correctly
            //but if we comment this row and add it inside produce() method the program fails
            System.out.println("Produced: " + i);
            try {
                produce(i);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
    }

    private void produce(int i) throws InterruptedException {

        while (sharedQueue.size() == SIZE) {
            synchronized (sharedQueue) {
                System.out.println("Queue is full " + Thread.currentThread().getName()
                        + " is waiting , size: " + sharedQueue.size());

                sharedQueue.wait();
            }
        }

        synchronized (sharedQueue) {
            //Here we can add and the program will fail
//            System.out.println("Produced:" + i);
            sharedQueue.add(i);
            sharedQueue.notifyAll();
        }
    }
}