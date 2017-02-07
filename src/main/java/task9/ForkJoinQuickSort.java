package task9;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by Maxim on 07.02.2017.
 */
public class ForkJoinQuickSort {
    private static final int SIZE = 100000;

    public static void main(String[] args) {
        int[] data1 = new int[SIZE];
        Random rand = new Random();
        for (int i = 0; i < data1.length; i++) {
            int randomNum = rand.nextInt(SIZE + 1);
            data1[i] = randomNum;
        }

        System.out.println("Array:" + Arrays.toString(data1));

        QuickSortAction quickSort = new QuickSortAction(data1);

        ForkJoinPool pool = new ForkJoinPool();
        long l = System.currentTimeMillis();
        pool.invoke(quickSort);


        pool.shutdown();
        long l1 = System.currentTimeMillis() - l;
        System.out.println((l1 / 1000) + " sec.");

        System.out.println("data = " + Arrays.toString(data1));
    }
}

class QuickSortAction extends RecursiveAction {
    private int[] data;
    private int left;
    private int right;

    public QuickSortAction(int[] data) {
        this.data = data;
        left = 0;
        right = data.length - 1;
    }

    public QuickSortAction(int[] data, int left, int right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }


    @Override
    protected void compute() {
        if (left < right) {
            int pivot = partition(data, left, right);
            invokeAll(new QuickSortAction(data, left, pivot),
                    new QuickSortAction(data, pivot + 1, right));
        }
    }

    private int partition(int[] array, int low, int high) {
        int pivot = array[low];
        int i = low - 1;
        int j = high + 1;
        while (true) {
            do {
                i++;
            }
            while (array[i] < pivot);

            do {
                j--;
            }
            while (array[j] > pivot);
            if (i >= j)
                return j;

            swap(array, i, j);
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
