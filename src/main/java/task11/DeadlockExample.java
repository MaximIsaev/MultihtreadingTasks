package task11;

import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Isaev_M.M. on 2/7/2017.
 */
public class DeadlockExample {

    private static final List<Integer> list = new CopyOnWriteArrayList<>();

    private static int summ = 0;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                list.add(RandomUtils.nextInt(0, 5));
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                System.out.println("SUMM: " + list.stream().mapToInt(Integer::intValue).sum());
            }
        });

        Thread t3 = new Thread(() -> {
            while (true) {
                for (Integer aList : list) {
                    summ += (Math.pow(aList, 2));
                }
                System.out.println("Square root: " + Math.sqrt(summ));
                summ = 0;
            }
        });

        t1.start();
        t2.start();
        t3.start();

    }
}
