package task4.com;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author Isaev_M.M. on 1/11/2017.
 */
public class Race {

    private static Logger log = Logger.getLogger(Race.class);

    private static final CyclicBarrier BARRIER = new CyclicBarrier(3, new Judge());

    private static final Queue<Car> rate = new LinkedList<>();

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(3);

        /**
         * Submit Car threads 1 and 2 one by one to cyclicBarrier
         * and then run them simultaneously and parallel
         */
        for (int i = 0; i < 2; i++) {
            Thread.sleep(1000);
            service.submit(new Car("Car" + (i + 1), (long) (Math.random() * 1000)));
        }

        /**
         * Submit Car3 thread to interrupt(disqualify) it later
         */
        Future<Boolean> car3 = service.submit(new Car("Car3", (long) (Math.random() * 1000)));

        /**
         * Interrupt Car3 thread after 5 seconds
         */
        try {
            car3.get(5, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            log.info("Car3 was disqualified!");
            car3.cancel(true);
        }

        service.shutdown();

        /**
         * Block all actions while all cars aren't finished
         */
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        log.info("Race is over!");
        Thread.sleep(1000);

        /**
         * Get the first element from Queue to determine the winner
         */
        log.info("The winner is " + rate.peek().name);

    }

    public static class Judge implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(500);
                log.info("Race has start!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Car implements Callable<Boolean> {

        private static Logger log = Logger.getLogger(Car.class);

        private static final long MAX_DISTANCE = 5000;

        private long friction;
        private long distance;
        private String name;

        public Car(String name, long friction) {
            this.name = name;
            this.friction = friction;
        }

        @Override
        public Boolean call() {

            try {
                log.info("Car " + this.name + " is on start!");
                BARRIER.await();

                while (distance < MAX_DISTANCE) {
                    Thread.sleep(friction);
                    distance += 100;
                    log.info(name + " " + distance);
                }

                /**
                 * After car has finished the race, add it to Queue
                 * to use later to determine the winner
                 */
                rate.add(this);
                log.info(this.name + " has finished!");
            } catch (InterruptedException e) {
                log.error(e);
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            return true;
        }
    }


}
