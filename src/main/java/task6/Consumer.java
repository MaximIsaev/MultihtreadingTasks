package task6;

import java.util.Queue;

/**
 * Created by Maxim on 03.02.2017.
 */
public class Consumer implements Runnable {

    private final Queue sharedQueue;
    private final int SIZE;

    public Consumer(Queue sharedQueue, int size) {
        this.sharedQueue = sharedQueue;
        this.SIZE = size;
    }

    @Override
    public void run() {
        while (true) {
            try {
                consume();
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
    }

    private void consume() throws InterruptedException {
        while (sharedQueue.isEmpty()) {
            synchronized (sharedQueue) {
                System.out.println("Queue is empty " + Thread.currentThread().getName()
                        + " is waiting , size: " + sharedQueue.size());

                sharedQueue.wait();
            }
        }

        synchronized (sharedQueue) {
            sharedQueue.notifyAll();
            System.out.println("Consume: " + sharedQueue.poll());
        }
    }
}