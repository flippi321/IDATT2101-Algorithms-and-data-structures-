package Algdatt.Oppgave2;
import java.util.Date;

public class Oppgave2 {
    public static class Exp{
        public double twoPartExp(double base, int expo){
            if (expo == 0)  return 1;
            else if (expo > 0){
                return base * twoPartExp(base, (expo-1));
            }
            return 0;
        }

        public double threePartExp(double base, int expo){
            if (expo == 0)  return 1;
                // Even Numbers
            else if (expo % 2 == 0) {
                return threePartExp(base*base,expo/2);
            }
            // Odd Numbers
            else if (expo % 2 == 1){
                return base * threePartExp(base*base,(expo-1)/2);
            }
            return 0;
        }
    }

    public static class Tester {
        Exp exp;
        Date start;
        Date end;
        int rounds;
        double time;
        String timeString;

        public Tester() {
            this.exp = new Exp();
        }

        public void test(double base, int expo){
            double oldtime = testTwoPart(base, expo);
            testThreePart(base, expo, oldtime);
            testMathExp(base, expo, oldtime);
        }

        public double testTwoPart(double base, int expo) {
            Date start = new Date();
            rounds = 0;
            Date end;

            // 2.1-1
            do {
                exp.twoPartExp(base, expo);
                end = new Date();
                rounds++;
            } while (end.getTime()-start.getTime() < 1000);
            time = (double)
                    (end.getTime()-start.getTime()) / rounds;

            if (time >= 0.1){
                timeString = String.format("%10.2f ms",  time);
            } else {
                timeString = String.format("%10.2f ns",  time*1000000);
            }

            System.out.printf("%10s %10s %30s %10s\n", "2.1-1", expo, timeString, "");
            return time;
        }

        public void testThreePart(double base, int expo, double oldTime){
            Date start = new Date();
            rounds = 0;
            Date end;

            // 2.2-3
            do {
                exp.threePartExp(base, expo);
                end = new Date();
                rounds++;
            } while (end.getTime()-start.getTime() < 1000);
            time = (double)
                    (end.getTime()-start.getTime()) / rounds;

            if (time >= 0.1){
                timeString = String.format("%10.2f ms",  time);
            } else {
                timeString = String.format("%10.2f ns",  time*1000000);
            }

            System.out.printf("%10s %10s %30s %10.5f\n", "2.2-3", expo, timeString, time/oldTime-1);
        }

        public void testMathExp(double base, int expo, double oldTime){
            Date start = new Date();
            rounds = 0;
            Date end;

            // Math.exp()
            do {
                Math.pow(base, expo);
                end = new Date();
                rounds++;
            } while (end.getTime()-start.getTime() < 1000);
            time = (double)
                    (end.getTime()-start.getTime()) / rounds;

            if (time >= 0.1){
                timeString = String.format("%10.2f ms",  time);
            } else {
                timeString = String.format("%10.2f ns",  time*1000000);
            }

            System.out.printf("%10s %10s %30s %10.3f\n", "Math.pow", expo, timeString, time/oldTime-1);
        }
    }

    public static void main(String[] args){
        // Testing that the methods work
        Exp exp = new Exp();
        System.out.println("Testing Methods...");
        if (exp.twoPartExp(2, 12) == 4096 && exp.threePartExp(2, 12) == 4096
        && exp.twoPartExp(3, 14) == 4782969 && exp.threePartExp(3, 14) == 4782969){
            System.out.println("Success\n");
        } else System.out.println("Fail\n");

        // Calculating Time usage of the different methods
        System.out.println("Time Usage:\n");
        System.out.printf("%10s %10s %30s %10s\n", "Type", "n", "Time:", "Change:");
        System.out.println("---------------------------------------------------------------");
        double base = 3.1415;

        // (pi)^100
        Tester tester = new Tester();
        tester.test(base, 100);
        System.out.print("\n");

        // (pi)^1000
        tester = new Tester();
        tester.test(base, 1000);
        System.out.print("\n");

        // (pi)^10000
        tester = new Tester();
        tester.test(base, 10000);
        System.out.print("\n");
    }
}