package Algdatt.Oppgave4;

import java.util.Scanner;
/**
 * The Iterator and Node methods used in this example were taken from the curriculum
 * But were modified to better suit this programs task
 */
public class Oppgave4 {
    static class Node {
        double element;
        Node next;
        Node previous;

        /**
         * Constructor for object that represents an element of the Double linked List
         * @param e the value this element represents
         * @param next the next value in the list
         * @param previous the previous value in the list
         */
        public Node(double e, Node next, Node previous) {
            this.element = e;
            this.next = next;
            this.previous = previous;
        }

        public double getElement() {
            return element;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrevious() {
            return previous;
        }
    }

    static class Iterator {
        private Node position;

        /**
         * Constructor for class that iterates through the Linked List
         * @param l the list that the iterator should associate with
         */
        public Iterator(DoubleLinkedList l) {
            position = l.getHead();
        }

        public boolean end() {
            return position == null;
        }

        public void lastElement(){
            while(position.getNext()!=null) {
                position = position.getNext();
            }
        }

        public double findElement() {
            if (!end()) {
                return position.getElement();
            } else {
                return Double.NaN;
            }
        }

        public void next() {
            if (!end()) {
                position = position.getNext();
            }
        }

        public void previous() {
            if (!end()) {
                position = position.getPrevious();
            }
        }
    }

    /**
     * Class that represents a Double Linked list
     * The code was taken from "Algoritmer og datastrukturer" By H. Haftig & M. Ljosland
     */
    static class DoubleLinkedList {
        private Node head = null;
        private Node tail = null;
        private int numberOfElements = 0;

        public Node getHead() {
            return head;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void insertInFront (double value) {
            head = new Node(value, head, null);
            if(tail == null) {
                tail = head;
            } else {
                head.next.previous = head;
            }
            numberOfElements++;
        }

        public void insertInBack(double value) {
            Node nyNode = new Node(value, null, tail);
            if(tail != null) {
                tail.next = nyNode;
            } else {
                head = nyNode;
            }
            tail = nyNode;
            numberOfElements++;
        }

        public Node remove(Node n) {
            if (n.previous != null) {
                n.previous.next = n.next;
            } else {
                head = n.next;
            }
            if (n.next != null) {
                n.next.previous = n.previous;
            } else {
                tail = n.previous;
            }
            n.next = null;
            n.previous = null;
            numberOfElements--;
            return n;
        }

        public Node findNr(int nr) {
            Node thisNode = head;
            if (nr < numberOfElements) {
                for (int i = 0; i < nr; i++) {
                    thisNode = thisNode.getNext();
                    return thisNode;
                }
            }
            return null;
        }

        public void deleteAll() {
            head = null;
            tail = null;
            numberOfElements = 0;
        }
    }

    public static class DoubleLinkedCalculator{
        DoubleLinkedList number1;
        DoubleLinkedList number2;

        /**
         * Constructor for class that performs addition and subtraction on long numbers
         * They are inputted as strings from the console, converted to DoubleLinkedList where each digit is an element
         * The result of the calculation is stored as a Linked List before it is converted to string and exported
         */
        public DoubleLinkedCalculator() {
            number1 = new DoubleLinkedList();
            number2 = new DoubleLinkedList();
        }

        public DoubleLinkedList generateLinkedListFromString(String number) {
            DoubleLinkedList list = new DoubleLinkedList();;

            for (int i = 0; i < number.length(); i++) {
                int digit = Integer.parseInt(String.valueOf(number.charAt(i)));
                list.insertInBack(digit);
            }

            return list;
        }

        /**
         * Method to add two lists together
         * @param listOne the first list
         * @param listTwo the list that is added
         * @return the result of the operation, as a String
         */
        public String additionOfTwoLists(DoubleLinkedList listOne, DoubleLinkedList listTwo){
            int digit1;
            int digit2;
            int carry = 0;
            Iterator it1 = new Iterator(listOne);
            Iterator it2 = new Iterator(listTwo);
            it1.lastElement();
            it2.lastElement();
            DoubleLinkedList additionResult = new DoubleLinkedList();
            while (!it1.end() | !it2.end()) {
                if(!it1.end()){
                    digit1 = (int) it1.findElement();
                } else {
                    digit1 = 0;
                }
                it1.previous();

                if(!it2.end()){
                    digit2 = (int) it2.findElement();
                } else {
                    digit2 = 0;
                }
                it2.previous();

                // Adding the sum to the sum_list
                int sum = digit1 + digit2 + carry;
                carry = 0;

                if (sum > 9){
                    sum -= 10;
                    carry = 1;
                }
                additionResult.insertInBack(sum);
            }
            if (carry == 1){
                additionResult.insertInBack(1);
            }

            return listToString(additionResult, null);
        }

        /**
         * Method to subtract two lists
         * If the second list is larger than the first one, it switches their places and adds a "-"
         * @param listOne the original list
         * @param listTwo the list that is subtracted with
         * @return the result of the operation, as a String
         */
        public String subtractionOfTwoLists(DoubleLinkedList listOne, DoubleLinkedList listTwo) {
            DoubleLinkedList result;
            result = subtract(listOne, listTwo);

            // If the second number is larger than the first, we must switch the numbers and add a "-" in front
            if (result == null){
                result = subtract(listTwo, listOne);
                return listToString(result, "-");
            }

            return listToString(result, null);
        }

        /**
         * This method is used in subtractionOfTwoLists and calculates one subtracted by the other
         * @param listOne the original list
         * @param listTwo the list that is subtracted with
         * @return the result of the operation, as a DoubleLinkedList
         */
        public DoubleLinkedList subtract(DoubleLinkedList listOne, DoubleLinkedList listTwo){
            int digit1;
            int digit2;
            int carry = 0;
            Iterator it1 = new Iterator(listOne);
            Iterator it2 = new Iterator(listTwo);
            it1.lastElement();
            it2.lastElement();
            DoubleLinkedList subtractionResult = new DoubleLinkedList();
            while (!it1.end() | !it2.end()) {
                if(!it1.end()){
                    digit1 = (int) it1.findElement();
                } else {
                    digit1 = 0;
                }
                it1.previous();

                if(!it2.end()){
                    digit2 = (int) it2.findElement();
                } else {
                    digit2 = 0;
                }
                it2.previous();

                // Adding the sum to the sum_list
                int sum = digit1 - digit2 + carry;
                carry = 0;

                if (sum < 0){
                    sum += 10;
                    carry = -1;
                }
                subtractionResult.insertInBack(sum);
            }

            // This would mean that the result would be negative since the second number is larger than the first
            if (carry == -1){
                return null;
            }
            return subtractionResult;
        }

        /**
         * Method that takes the numbers form the console, adds them together and returns the answer a String
         * @param number1 the first number in the operation as a String
         * @param number2 the second number in the operation as a String
         * @return a string showing both numbers, the operation and the answer, in an orderly manner
         */
        public String addTwoNumbers(String number1, String number2){
            String output = "\nAddition:\n";
            output += String.format("%3s %30s\n", " ", number1);
            output += String.format("%3s %30s\n", "+", number2);
            output += String.format("%3s %30s\n", "=",
                    additionOfTwoLists(generateLinkedListFromString(number1), generateLinkedListFromString(number2)));
            return output;
        }

        /**
         * Method that takes the numbers form the console, subtracts them and returns the answer a String
         * @param number1 the first number in the operation as a String
         * @param number2 the second number in the operation as a String
         * @return a string showing both numbers, the operation and the answer, in an orderly manner
         */
        public String subtractTwoNumbers(String number1, String number2){
            String output = "\nSubtraction:\n";
            output += String.format("%3s %30s\n", " ", number1);
            output += String.format("%3s %30s\n", "-", number2);
            output += String.format("%3s %30s\n", "=",
                    subtractionOfTwoLists(generateLinkedListFromString(number1), generateLinkedListFromString(number2)));
            return output;
        }

        public String listToString(DoubleLinkedList list, String prefix){
            String output = "";
            String number = "";
            if (prefix != null){
                output += prefix;
            }

            boolean leadingZero = true;
            Iterator it = new Iterator(list);
            it.lastElement();
            while (!it.end()){
                if(!leadingZero | (int) it.findElement()!=0){
                    leadingZero = false;
                    number += (int) it.findElement();
                }
                it.previous();
            }
            if (number.isEmpty()){
                number += "0";
            }
            output += number;
            return output.trim();
        }

        public void setNumber1(DoubleLinkedList number1) {
            this.number1 = number1;
        }

        public void setNumber2(DoubleLinkedList number2) {
            this.number2 = number2;
        }

        public DoubleLinkedList getNumber1() {
            return number1;
        }

        public DoubleLinkedList getNumber2() {
            return number2;
        }
    }

    public static void main(String[] args) {
        System.out.println("-------- EXERCISE 4 PART 1 --------\n");
        System.out.println("TESTS:");
        Scanner sc = new Scanner(System.in);
        String input = "";
        DoubleLinkedCalculator calculator = new DoubleLinkedCalculator();

        //Test set
        DoubleLinkedList number1 = calculator.generateLinkedListFromString("100000000019999999999001");
        DoubleLinkedList number2 = calculator.generateLinkedListFromString("100007");
        DoubleLinkedList number3 = calculator.generateLinkedListFromString("840000000000000200000");
        DoubleLinkedList number4 = calculator.generateLinkedListFromString("100000000007000060004");

        System.out.println("-----------------------------------");
        if (calculator.additionOfTwoLists(number1,number2).equals("100000000020000000099008")) {
            System.out.println("Addition test succeeded");

        } else {
            System.out.println("Addition test failed!");
        }

        if (calculator.subtractionOfTwoLists(number3, number4).equals("739999999993000139996")) {
            System.out.println("Subtraction test succeeded");
        } else {
            System.out.println("Subtraction test failed!");
        }
        System.out.println("-----------------------------------\n");

        do {
            System.out.println("Enter 0 for Exit");
            System.out.println("Enter 1 to add two numbers");
            System.out.println("Enter 2 to subtract two numbers");
            input = sc.next();

            if(input.equals("0")) {
                System.out.println("EXITING...");
            }
            else if (input.equals("1")) {
                System.out.println("WELCOME TO THE ADDITION CALCULATOR:\n");
                System.out.println("-----------------------------------------");
                System.out.println("Please enter the first number:");
                String numberOne = sc.next();
                System.out.println("Please enter the second number:");
                String numberTwo = sc.next();
                System.out.println("-----------------------------------------");
                System.out.println(calculator.addTwoNumbers(numberOne, numberTwo));
                System.out.println();
            } else if (input.equals("2")) {
                System.out.println("WELCOME TO THE SUBTRACTION CALCULATOR:\n");
                System.out.println("-----------------------------------------");
                System.out.println("Please enter the first number:");
                String numberOne = sc.next();
                System.out.println("Please enter the second number:");
                String numberTwo = sc.next();
                System.out.println("-----------------------------------------\n");
                System.out.println(calculator.subtractTwoNumbers(numberOne, numberTwo));
                System.out.println();
            } else {
                System.out.println("ERROR: The input must be a single digit number");
                input = sc.next();
            }
            System.out.println();
        } while (!input.equals("0"));
    }
}
