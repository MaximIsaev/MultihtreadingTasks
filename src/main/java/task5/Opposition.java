package task5;

import java.util.Random;

/**
 * @author Isaev_M.M. on 2/2/2017.
 */
public class Opposition {

    private final Object monitor = new Object();

    public class Counter {
        private int count = 3;

        public void increment() {
            count++;
        }

        public void decrement() {
            count--;
        }

        public int get() {
            return count;
        }
    }

    public class Wrestler implements Runnable {


        private Counter counter;
        private boolean increment;
        private Random rand;

        public Wrestler(Counter counter, boolean increment) {
            this.counter = counter;
            this.increment = increment;
            rand = new Random();
        }

        @Override
        public void run() {
            while (true) {

                if (increment) {
                    counter.increment();
                } else {
                    counter.decrement();
                }

                int x = counter.get();
                try {
                    System.out.println("Wrestler" + Thread.currentThread().getName() + " " + x);

                    if (t2.getState().equals(Thread.State.WAITING)) {
                        synchronized (monitor) {
                            System.out.println("Notified!");
                            monitor.notifyAll();
                        }
                    }

                    if (x == 0) {
                        synchronized (monitor) {
                            System.out.println("Wrestler" + Thread.currentThread().getName() + " is waiting...");
                            monitor.wait();
                        }
                    }

                    Thread.sleep(rand.nextInt(100));

                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    private Thread t1, t2;

    public static void main(String[] args) {
        new Opposition().start();
    }

    private void start() {
        Counter counter = new Counter();
        t1 = new Thread(new Wrestler(counter, true));
        t2 = new Thread(new Wrestler(counter, false));
        t1.start();
        t2.start();
        try {
            while (true) {
                Thread.sleep(100);
                if (!(t1.isAlive() && t2.isAlive())) {
                    break;
                }
            }
        } catch (InterruptedException e) {
        }
        System.out.println("Finished");
    }
}
