package Algdatt.Oppgave5;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Oppgave5 {

    public static class Hashtable {
        double A = (Math.sqrt(5)-1)/2;
        int[] table;
        int h1, h2, pos, collisions, size, totalValues;

        public Hashtable(int tableSize) {
            size = nextPrime((int) (tableSize * 1.34));
            this.table = new int[size];
            collisions = 0;
            totalValues = 0;
        }

        /**
         * Method to find the next prime from a number
         * @param n the number which should be checked
         * @return a prime number equal or above n
         */
        public int nextPrime(int n) {
            for (int i = 2; i < n; i++) {
                // n er ikke et primtall
                if (n % i == 0) {
                    n++;
                    i = 2;
                }
            }
            return n;
        }

        /**
         * Method used to find h1 used in the Double Hashing method
         * Formula taken from the book
         * @param k the key of the element that is going to be added
         * @param m the size of the hash-tables list
         * @return the position we should put the value in
         */
        public int hash1(int k, int m){
            double number = k * A;
            int rounded = (int) number;
            double result = number - rounded;
            return (int) (m * (result));
        }

        /**
         * Method used to find h2 used in the Double Hashing method
         * Formula taken from the book
         * @param k the key of the element that is going to be added
         * @param m the size of the hash-tables list
         * @return how long we should jump each time we find a used space
         */
        public int hash2(int k, int m) {
            return (k % (m - 1)) + 1;
        }

        /**
         * Method to add a number to the list using Double Hashing
         * @param n the number that should be added
         * @return if the number was successfully added or not
         */
        public boolean add(int n) {
            h1 = hash1(n, size);

            // If the first position is free, we add n
            if (table[h1] == 0) {
                table[h1] = n;
                totalValues++;
                return true;
            }

            // If not, we use Double Hashing
            else {
                h2 = hash2(n, size);
                pos = h1 + h2;

                while (pos != h1) {
                    while (pos > size - 1) {
                        pos -= size;
                    }
                    if (table[pos] == 0) {
                        table[pos] = n;
                        totalValues++;
                        return true;
                    }
                    pos += h2;
                    collisions++;
                }
            }
            // if there are no free slots
            return false;
        }

        /**
         * Method to add a list of numbers to the HashTable
         * @param list the numbers that are going to be added
         */
        public void addAll(int[] list){
            for(int i : list){
                add(i);
            }
        }

        /**
         * Method to find the position of a number n
         * If there are multiple instances of n, it will return the first of these found
         * @param n the number we want to find
         * @return the position of the element n, or -1 if the number is not found
         */
        public int findPos(int n) {
            h1 = hash1(n, size);

            // Checks if the n is at the optimal position
            if (table[h1] == n) {
                return h1;
            }

            // If not, we must use Double Hashing to check
            else {
                h2 = hash2(n, size);
                pos = h1 + h2;

                while (pos != h1) {
                    while (pos > size - 1) {
                        pos -= size;
                    }
                    if (table[pos] == n) {
                        return pos;
                    }
                    pos += h2;
                }
            }

            return -1;
        }

        /**
         * Method to remove an instance of a number in the list
         * @param n the number we want to remove
         */
        public void deleteNumber(int n) {
            table[findPos(n)] = 0;
            totalValues--;
        }

        /**
         * Method for calculating the load value of the hashtable.
         * @return a double representing the load value.
         */
        public double loadValue() {
            return (double) totalValues / table.length;
        }

        @Override
        public String toString() {
            String output = "";
            output += String.format("%5s %-20s %20s\n", "", "Collisions:", collisions);
            output += String.format("%5s %-20s %20.4f\n", "", "Load Factor:", loadValue());
            output += String.format("%5s %-20s %20s\n", "", "Total Elements:", totalValues);
            output += String.format("%5s %-20s %20s", "",  "Total Spaces", size);
            return output;
        }
    }

    public static class Tester {
        Hashtable hash1;
        HashMap<Integer, Integer> hash2;
        double time;
        double oldTime;
        int size;

        /**
         * Constructor for an object which times how long it takes to fill our HashTable
         * @param size the size of the stocks that should be analysed
         */
        public Tester(int size) {
            this.size = size;
            oldTime = 0;
        }

        /**
         * Method to test how long our HashTable uses to add a list of numbers to itself
         * @return how long time the StockBot used to finish the task
         */
        public String testOurHashTable(int[] list) {
            // Start timer
            long start = System.nanoTime();

            // Adds the elements to the hashlist
            hash1 = new Hashtable(size);
            hash1.addAll(list);

            // Stops the timer
            long end = System.nanoTime();

            // Calculate time, change and define new OldTime
            double time = TimeUnit.MILLISECONDS.toMillis((end-start));
            double change = (double) time/oldTime;
            if (oldTime == 0){
                change = 0;
            }
            oldTime = time;

            //Shorten time if necessary
            String measurment = "ns";
            if (time > 1000000000){
                time = time / 1000000000;
                measurment = "s";
            } else if (time > 1000000){
                time = time / 1000000;
                measurment = "ms";
            }

            return String.format("%15s %10s %10.2f%2s %19.2fx", "Our hashtable", size, time, measurment, change);
        }

        /**
         * Method to test how long the Default HashTable uses to add a list of numbers to itself
         * @return how long time the StockBot used to finish the task
         */
        public String testDefaultHashTable(int[] list) {
            // Start timer
            long start = System.nanoTime();

            // Adds the elements to the hashlist
            hash2 = new HashMap<Integer, Integer>(size);
            for (int i : list) {
                hash2.put(i, i);
            }

            // Stops the timer
            long end = System.nanoTime();

            // Calculate time, change and define new OldTime
            double time = TimeUnit.MILLISECONDS.toMillis((end - start));
            double change = (double) time / oldTime;
            if (oldTime == 0) {
                change = 0;
            }
            oldTime = time;

            //Shorten time if necessary
            String measurment = "ns";
            if (time > 1000000000) {
                time = time / 1000000000;
                measurment = "s";
            } else if (time > 1000000) {
                time = time / 1000000;
                measurment = "ms";
            }

            return String.format("%15s %10s %10.2f%2s %19.2fx", "Java's hashmap",size, time, measurment, change);
        }

        /**
         * Generates random numbers and filla an array.
         * @param size is the desired size of list.
         * @param min is the minimum value.
         * @param max is the maximum value.
         */
        public static int[] fillListWithRandomNumbers (int size, int min, int max) {
            int[] randomArray = new int[size];
            for (int i = 0; i < size; i++) {
                randomArray[i] = (int) ((Math.random() * (max - min + 1)) + min);
            }
            return randomArray;
        }
    }

    public static void main(String[] args) {
        int size = 10000000;
        int MAX = 100000000;
        int MIN = 0;

        // Test the two HashMap functions
        Tester tester = new Tester(size);
        int[] list = tester.fillListWithRandomNumbers(size, MIN, MAX);
        String line = "------------------------------------------------------------";
        System.out.println(line);
        System.out.println("                         TIME USAGE:");
        System.out.println(line);
        System.out.printf("%15s %10s %12s %20s\n", "Type", "Size", "Time", "Times slower");
        System.out.println(tester.testOurHashTable(list));
        System.out.println(tester.testDefaultHashTable(list));
        System.out.println(line);
        System.out.println("              Our hashtable function info:");
        System.out.println(line);
        System.out.println(tester.hash1);
        System.out.println(line);
    }
}
