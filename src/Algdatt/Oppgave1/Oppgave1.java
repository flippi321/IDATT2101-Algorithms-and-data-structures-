package Algdatt.Oppgave1;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chribrev
 * @version 1.0
 */
public class Oppgave1 {
    public static class StockBot {
        // Variables
        private int buyValue;
        private int sellValue;

        // Current maximum Trade
        private int maxChange;
        private int maxBuy;
        private int maxSell;
        private int maxTradeValue;
        List<Integer> stocks;

        /**
         * Constructor for creating a stockBot which will analyse a set list of stock values
         * @param stocks the stocks that the bot should analyse
         */
        public StockBot(List<Integer> stocks) {
            this.stocks = stocks;
            buyValue = 0;
            sellValue = 0;
            maxChange = -1;
            maxBuy = 0;
            maxSell = 0;
        }

        /**
         * Empty constructor for creating a stockBot which will analyse a set list of stock values
         * The stocks must be defined before they can be analysed
         */
        public StockBot() {
            this.stocks = new ArrayList<>();
            buyValue = 0;
            sellValue = 0;
            maxChange = -1;
            maxBuy = 0;
            maxSell = 0;
        }

        public List<Integer> getStocks() {
            return stocks;
        }

        /**
         * Method to fill the stock list with a set number of values between -100 and 100
         * @param size the amount of days in the stock list
         */
        public void fill(int size){
            stocks.clear();
            for (int i = 0; i < size; i++){
                double random = Math.random();
                stocks.add((int) (random * 200) - 100);
            }
        }

        /**
         * Method to calculate the most profitable combination of buying and selling stocks
         */
        public void calculate(){
            // Initializes the values
            buyValue = stocks.get(0);
            sellValue = stocks.get(1);
            maxChange = sellValue - buyValue;

            // Loop starting on first element and finishing at the second last one
            // Will not go through the last day since you can't sell afterwards
            for(int buy = 0; buy < (stocks.size()-1); buy++) {
                buyValue += stocks.get(buy);
                sellValue = 0;

                // Loop starting from the next element and finishing at the last element
                // This is since you can't sell before buying and selling on the same day isn't profitable
                for (int sell = (buy + 1); sell < stocks.size(); sell++) {
                    sellValue += stocks.get(sell);
                    int change = (sellValue - buyValue);

                    if (change > maxChange) {
                        maxChange = change;
                        maxBuy = buy + 1;     // Change from 0-indexation to 1-indexastion
                        maxSell = sell + 1;   // Change from 0-indexation to 1-indexastion
                    }
                }
            }
        }

        /**
         * Returns the values of the stockBot in a table
         * @return a table containing when the best day to sell or buy is and the profits of this trade
         */
        @Override
        public String toString() {
            String output = String.format("%30s\n", "StockBot20000:");
            output += ("------------------------------------------------\n");
            output += String.format("%20s: %10d\n", "Best time to buy", maxBuy);
            output += String.format("%20s: %10d\n", "Best time to sell", maxSell);
            output += ("------------------------------------------------\n");
            return output;
        }
    }

    public static class Tester {
        StockBot stockBot;
        Date start;
        Date end;
        int rounds;
        double time;
        double oldTime;
        int size;

        /**
         * Constructor for an object which times how long a stockBot instance uses to analyse a set of stocks
         * @param size the size of the stocks that should be analysed
         * @param oldTime the time of a previous instance, used to compare with the new results
         */
        public Tester(int size, double oldTime) {
            this.size = size;
            this.oldTime = oldTime;
            stockBot = new StockBot();
            stockBot.fill(size);
            start = new Date();
            rounds = 0;
        }

        /**
         * Constructor for an object which times how long a stockBot instance uses to analyse a set of stocks
         * @param size the size of the stocks that should be analysed
         */
        public Tester(int size) {
            this.size = size;
            oldTime = 1;
            start = new Date();
            rounds = 0;
            stockBot = new StockBot();
            stockBot.fill(size);
        }

        /**
         * Method to test how long a stockBot instance uses to analyse a set of stocks
         * @return information about the stockBot and how much time it used, in a table
         */
        public String test(){
            do {
                stockBot.calculate();
                end = new Date();
                rounds++;
            } while (end.getTime()-start.getTime() < 1000);
            time = (double)
                    (end.getTime()-start.getTime()) / rounds;

            return String.format("%10s %20.4f %10.2f", size, time, time/oldTime);
        }

        public double getTime() {
            return time;
        }
    }

    public static void main(String[] args){
        double oldTime = 1;
        System.out.println("Calculations:\n");
        System.out.printf("%10s %20s %10s", "n", "Time (Miliseconds)", "Change\n");

        // Test program with 100 values
        Tester tester = new Tester(100);
        System.out.println(tester.test());
        oldTime = tester.getTime();

        // Test program with 1000 values
        tester = new Tester(1000, oldTime);
        System.out.println(tester.test());
        oldTime = tester.getTime();

        // Test program with 10000 values
        tester = new Tester(10000, oldTime);
        System.out.println(tester.test());
        oldTime = tester.getTime();

        // Test program with 100000 values
        tester = new Tester(100000, oldTime);
        System.out.println(tester.test());
        oldTime = tester.getTime();
    }

    // My Results
    //         n   Time (Miliseconds)    Change
    //       100               0,0109       0,01
    //      1000               0,8002      73,22
    //     10000              75,8571      94,80
    //    100000            7695,0000     101,44
}