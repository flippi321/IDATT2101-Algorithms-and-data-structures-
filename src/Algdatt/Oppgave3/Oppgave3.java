package Algdatt.Oppgave3;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Exercise 3 program
 * @version 1.0
 * @author johnfb, chribrev, bjornjob, teodorbi
 * @since 13.09.22
 */

public class Oppgave3 {
    public static int[] testArray1 = {24, 8, 8, 8, 7, 4, 99, 1000000, 1000000, 42, 75, 29, 77, 38, 57};
    public static int[] testArray2 = {24, 8, 8, 8, 7, 4, 99, 1000000, 1000000, 42, 75, 29, 77, 38, 57};
    public static int[] optimalAlternatingArray = {5, 5, 5, 5, 5, 10, 10, 10, 10, 10};
    public static int[] expectedArray = {4, 7, 8, 8, 8, 24, 29, 38, 42, 57, 75, 77, 99, 1000000, 1000000};
    private static final String TIME_FORMAT = "%-20s %-15s %-10s %-10s %n";
    private static final String TABLE_LINE = "-------------------------------------------------------------";


    /**
     * Generates a random array of integers
     * @param size Number of integers to generate
     * @return list of integers
     */
    public static int[] createRandomArray(int size) {
        int[] array = new int[size];
        Random r = new Random();

        for (int i = 0; i < size; i++) {
            array[i] = (r.nextInt((110000 - 1) + 1) - 10000);
        }
        return array;
    }

    /**
     * Generates a sorted array of integers
     * @param size Number of integers to generate
     * @return list of integers
     */
    public static int[] createSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    public static int checksum(int[] array) {
        int sum = 0;
        for (int i : array) sum += i;
        return sum;
    }

    /**
     * Generates an array containing two alternating integers
     * @param size Number of integers to generate
     * @return list of integers
     */
    public static int[] createAlternatingArray(int size, int var1, int var2) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                array[i] = var1;
            } else {
                array[i] = var2;
            }
        }
        return array;
    }

    public static class SortingClass {
        public static void quicksort(int[] t, int v, int h) {
            if (h - v > 2) {
                int delepos = split(t, v, h);
                quicksort(t, v, delepos - 1);
                quicksort(t, delepos + 1, h);
            } else median3sort(t, v, h);
        }

        private static int split(int[] t, int v, int h) {
            int iv, ih;
            int m = median3sort(t, v, h);
            int dv = t[m];
            swap(t, m, h - 1);
            for (iv = v, ih = h - 1;;) {
                while (t[++iv] < dv);
                while (t[--ih] > dv);
                if (iv >= ih) break;
                swap(t, iv, ih);
            }
            swap(t, iv, h - 1);
            return iv;
        }

        private static int median3sort(int[] t, int v, int h) {
            int m = (v + h) / 2;
            if (t[v] > t[m]) swap(t, v, m);
            if (t[m] > t[h]) {
                swap(t, m, h);
                if (t[v] > t[m]) swap(t, v, m);
            }
            return m;
        }

        static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }


        static void dualPivotQuickSort(int[] arr, int low, int high) {
            if (low < high) {
                // piv[] stores left pivot and right pivot.
                // piv[0] means left pivot and
                // piv[1] means right pivot
                int[] piv = partition(arr, low, high);

                dualPivotQuickSort(arr, low, piv[0] - 1);
                dualPivotQuickSort(arr, piv[0] + 1, piv[1] - 1);
                dualPivotQuickSort(arr, piv[1] + 1, high);
            }
        }

        static int[] partition(int[] arr, int low, int high) {
            swap(arr, low, low + (high - low) / 3);
            swap(arr, high, high - (high - low) / 3);

            if (arr[low] > arr[high])
                swap(arr, low, high);

            // p is the left pivot, and q
            // is the right pivot.
            int j = low + 1;
            int g = high - 1, k = low + 1,
                    p = arr[low], q = arr[high];

            while (k <= g) {
                // If elements are less than the left pivot
                if (arr[k] < p) {
                    swap(arr, k, j);
                    j++;
                }

                // If elements are greater than or equal
                // to the right pivot
                else if (arr[k] >= q) {
                    while (arr[g] > q && k < g)
                        g--;

                    swap(arr, k, g);
                    g--;

                    if (arr[k] < p) {
                        swap(arr, k, j);
                        j++;
                    }
                }
                k++;
            }
            j--;
            g++;

            // Bring pivots to their appropriate positions.
            swap(arr, low, j);
            swap(arr, high, g);

            // Returning the indices of the pivots
            // because we cannot return two elements
            // from a function, we do that using an array.
            return new int[]{j, g};
        }
    }

    public static class RunTimeTestClass {
        // Global variable used for tracking change
        public static double time;
        public static double oldTime;

        public static int[] runTimeTestQuickSort(int[] array) {
            int[] array2 = array.clone();
            Date end;
            Date start = new Date();
            SortingClass.quicksort(array2, 0, array2.length-1);
            end = new Date();
            time = (double) (end.getTime() - start.getTime());

            if (oldTime == 0){
                System.out.printf(TIME_FORMAT, "QuickSort", array2.length, time + "ms", "");
            } else if (oldTime > 0){
                System.out.printf(TIME_FORMAT, "QuickSort", array2.length, time + "ms",
                        String.format("%5.2f %-5s", (int) 100*(time/oldTime-1), "%"));;
            }
            oldTime = time;
            return array2;
        }

        public static int[] runTimeTestDualPivot(int[] array) {
            int[] array2 = array.clone();
            Date end;
            Date start = new Date();
            SortingClass.dualPivotQuickSort(array2, 0, array2.length - 1);
            end = new Date();
            time = (double) (end.getTime() - start.getTime());
            if (oldTime == 0){
                System.out.printf(TIME_FORMAT, "Dual Pivot sort", array2.length, time + "ms", "");
            } else if (oldTime > 0){
                System.out.format(TIME_FORMAT, "Dual Pivot sort", array2.length, time + "ms",
                        String.format("%5.2f %-5s", 100*(time/oldTime-1), "%"));
            }
            oldTime = time;
            return array2;
        }
    }

    public static class SortingTester {
        public static boolean testSortedArray() {
            int[] originalArray = createSortedArray(1000);
            int[] sortedArray1 = createSortedArray(1000);
            int[] sortedArray2 = createSortedArray(1000);
            SortingClass.dualPivotQuickSort(sortedArray1, 0, sortedArray1.length-1);
            SortingClass.quicksort(sortedArray1, 0, sortedArray1.length-1);
            if (Arrays.equals(originalArray, sortedArray2) && Arrays.equals(originalArray, sortedArray2)) {
                System.out.println("Test succeeded");
                return true;
            }

            System.out.println("Test failed");
            return false;
        }

        public static boolean testAlternatingArray() {
            int[] alternatingArray1 = createAlternatingArray(10, 5, 10);
            int[] alternatingArray2 = createAlternatingArray(10, 5, 10);
            SortingClass.dualPivotQuickSort(alternatingArray1, 0, alternatingArray1.length-1);
            SortingClass.quicksort(alternatingArray2,0, alternatingArray2.length -1);
            if (Arrays.equals(alternatingArray1, optimalAlternatingArray) &&
                    Arrays.equals(alternatingArray2, optimalAlternatingArray)) {
                System.out.println("Test succeeded");
                return true;
            }

            System.out.println("Test failed");
            return false;
        }

        public static boolean testRandomArray() {
            SortingClass.dualPivotQuickSort(testArray1, 0, testArray1.length-1);
            SortingClass.quicksort(testArray2, 0, testArray1.length-1);
            if (Arrays.equals(testArray1, expectedArray) && Arrays.equals(testArray2, expectedArray)) {
                System.out.println("Test succeeded");
                return true;
            }
            System.out.println("Test failed");
            return false;
        }

        public static void testResults(int[] originalArray, int[] quickSorted, int[] dualPivotSorted){
            if (checkOrder(quickSorted) && checkOrder(dualPivotSorted)) {
                if (checkSum(originalArray) == checkSum(quickSorted) &&
                        checkSum(originalArray) == checkSum(dualPivotSorted)){
                    System.out.println("Both arrays had valid answers :-)");
                    return;
                }
            }
            System.out.println("There was a problem with the answers...");
        }

        public static int checkSum(int[] array){
            int sum = 0;
            for (int j : array) {
                sum += j;
            }
            return sum;
        }

        public static boolean checkOrder(int[] array){
            for (int i = 0; i < array.length -2; i++) {
                if (!(array[i+1] >= array[i])){
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println("----- STARTING APPLICATION -----\n");

        System.out.println("Running Tests:");
        SortingTester.testRandomArray();
        SortingTester.testAlternatingArray();
        SortingTester.testSortedArray();

        System.out.println("\n\nTask 1 - sorting arrays with random data");
        System.out.format(TIME_FORMAT, "Type", "Array Size", "Time", "Change");
        System.out.println(TABLE_LINE);

        int[] originalArray = createRandomArray(1000000);
        int[] quickSorted = RunTimeTestClass.runTimeTestQuickSort(originalArray);
        int[] dualPivotSorted = RunTimeTestClass.runTimeTestDualPivot(originalArray);
        SortingTester.testResults(originalArray, quickSorted, dualPivotSorted);
        RunTimeTestClass.oldTime = 0;
        System.out.println();

        originalArray = createRandomArray(10000000);
        quickSorted = RunTimeTestClass.runTimeTestQuickSort(originalArray);
        dualPivotSorted = RunTimeTestClass.runTimeTestDualPivot(originalArray);
        SortingTester.testResults(originalArray, quickSorted, dualPivotSorted);
        RunTimeTestClass.oldTime = 0;
        System.out.println();

        originalArray = createRandomArray(100000000);
        quickSorted = RunTimeTestClass.runTimeTestQuickSort(originalArray);
        dualPivotSorted = RunTimeTestClass.runTimeTestDualPivot(originalArray);
        SortingTester.testResults(originalArray, quickSorted, dualPivotSorted);
        RunTimeTestClass.oldTime = 0;
        System.out.println("\n\n");

        System.out.println(TABLE_LINE);
        System.out.println("Task 2 - sorting arrays with duplicates");
        System.out.format(TIME_FORMAT, "Type", "Array Size", "Time", "Change");
        System.out.println(TABLE_LINE);

        originalArray = createAlternatingArray(1000000, 4, 8);
        quickSorted = RunTimeTestClass.runTimeTestQuickSort(originalArray);
        dualPivotSorted = RunTimeTestClass.runTimeTestDualPivot(originalArray);
        SortingTester.testResults(originalArray, quickSorted, dualPivotSorted);
        RunTimeTestClass.oldTime = 0;
        System.out.println();

        originalArray = createAlternatingArray(10000000, 4, 8);
        quickSorted = RunTimeTestClass.runTimeTestQuickSort(originalArray);
        dualPivotSorted = RunTimeTestClass.runTimeTestDualPivot(originalArray);
        SortingTester.testResults(originalArray, quickSorted, dualPivotSorted);
        RunTimeTestClass.oldTime = 0;
        System.out.println();

        originalArray = createAlternatingArray(100000000, 4, 8);
        quickSorted = RunTimeTestClass.runTimeTestQuickSort(originalArray);
        dualPivotSorted = RunTimeTestClass.runTimeTestDualPivot(originalArray);
        SortingTester.testResults(originalArray, quickSorted, dualPivotSorted);
        RunTimeTestClass.oldTime = 0;
        System.out.println("\n\n");

        System.out.println(TABLE_LINE);
        System.out.println("Task 3 - sorting sorted arrays");
        System.out.format(TIME_FORMAT, "Type", "Array Size", "Time", "Change");
        System.out.println(TABLE_LINE);

        originalArray = createSortedArray(1000000);
        quickSorted = RunTimeTestClass.runTimeTestQuickSort(originalArray);
        dualPivotSorted = RunTimeTestClass.runTimeTestDualPivot(originalArray);
        SortingTester.testResults(originalArray, quickSorted, dualPivotSorted);
        RunTimeTestClass.oldTime = 0;
        System.out.println();

        originalArray = createSortedArray(10000000);
        quickSorted = RunTimeTestClass.runTimeTestQuickSort(originalArray);
        dualPivotSorted = RunTimeTestClass.runTimeTestDualPivot(originalArray);
        SortingTester.testResults(originalArray, quickSorted, dualPivotSorted);
        RunTimeTestClass.oldTime = 0;
        System.out.println();

        originalArray = createSortedArray(100000000);
        quickSorted = RunTimeTestClass.runTimeTestQuickSort(originalArray);
        dualPivotSorted = RunTimeTestClass.runTimeTestDualPivot(originalArray);
        SortingTester.testResults(originalArray, quickSorted, dualPivotSorted);
        RunTimeTestClass.oldTime = 0;
        System.out.println("\n\n");
    }
}