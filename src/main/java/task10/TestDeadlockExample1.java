package task10;

/**
 * @author Isaev_M.M. on 2/6/2017.
 */
public class TestDeadlockExample1 {


    public static void main(String[] args) {
        final String resource1 = "dfsdfgf";
        final String resource2 = "ghdfg";
        final String resource3 = "fgdfsgd";

        Thread t1 = new Thread() {
            public void run() {
                synchronized (resource1) {
                    System.out.println("Thread 1: locked resource 1");

                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }

                    synchronized (resource2) {
                        System.out.println("Thread 1: locked resource 2");
                    }
                }
            }
        };

        Thread t2 = new Thread() {
            public void run() {
                synchronized (resource2) {
                    System.out.println("Thread 2: locked resource 2");

                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }

                    synchronized (resource3) {
                        System.out.println("Thread 2: locked resource 1");
                    }
                }
            }
        };

        Thread t3 = new Thread() {
            public void run() {
                synchronized (resource3) {
                    System.out.println("Thread 3: locked resource 3");
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }

                    synchronized (resource1) {
                        System.out.println("Thread 3: locked resource 1");
                    }
                }
            }

        };

        t1.start();
        t2.start();
        t3.start();
    }
}