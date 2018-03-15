package lt.itswedbankacade.interestcalculator;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
    JAVA DAY2 MONDAY 03-05
*/
public class Main {

    public static void main(String[] args) {

        //Commented Task 3 results

        //    double[] interimInterest;
        //     double interestRate = scanner.nextInt();
        //   interimInterest = new double[arraySize];
        //     for (int i = 1; i < periodYears +1 ; i++) {
        //         System.out.printf("Interest amount after year " + i + " :" + "%.2f\n", interestCalculatorYear(i, amount, interestRate, periodicly));
        //   }
        /* System.out.println(Arrays.toString(interimInterest)); */

        //Validation
        Scanner scanner = new Scanner(System.in);
        //This way too complicated than it needs to be. It would be enough to validate fields in separate blocks instead of nesting.
        while (true) {

            //"printf" should be used only when you want to format something. Here you should use "print" instead.
            System.out.printf("Amount: ");
            //Amount is not protected from invalid number input (entering letters instead of number)
            double amount = scanner.nextDouble();
            if (amount <= 0) System.out.println("Invalid input! Try again!");
            else {

                while (true) {
                    //"printf" should be used only when you want to format something. Here you should use "print" instead.
                    System.out.printf("Period years: ");
                    try {
                        int periodYears = scanner.nextInt();
                        if (periodYears <= 0) System.out.println("Invalid input! Try again!");
                        else {

                            //I see that you don't fully get how dynamic array expansion works. Follow these steps:
                            // 1. Create a variable for an empty array: "double[] interestRates = new double[0];"
                            // 2. Read input: "double rate = scanner.nextDouble();"
                            // 3. Validate input: ...
                            // 4. Expand array by 1 element: "interestRates = Arrays.copyOf(interestRates, interestRates.length + 1);"
                            // 5. Add validated input at the end of expanded array: "interestRates[interestRates.length - 1] = rate;"
                            // 6. Repeat
                            double[] newArr = new double[10000];
                            double[] interestRates;
                            int indexSize = 0;

                            do {

                                //"printf" should be used only when you want to format something. Here you should use "print" instead.
                                System.out.printf("Interest rate: ");
                                double rate = scanner.nextDouble();

                                if (rate < 0 || rate > 100) System.out.println("Invalid input! Try again!");
                                else {

                                    if (rate == 0) {
                                        break;
                                    } else {
                                        newArr[indexSize] = rate;
                                        indexSize++;
                                    }
                                }
                            }
                            while (true);

                            interestRates = Arrays.copyOf(newArr, indexSize);

                            while (true) {
                                //"printf" should be used only when you want to format something. Here you should use "print" instead.
                                System.out.printf("Compound frequency: ");
                                String character = scanner.next();
                                char period = character.charAt(0);

                                if (period == 'Y' || period == 'D' || period == 'W' || period == 'Q' || period == 'H' || period == 'M') {

                                    double periodicly;
                                    periodicly = switchFrequency(period);

                                    //You don't need to cast int to int ("(int) periodYears")
                                    int arraySize = (int) ((int) periodYears * periodicly);
                                    double[][] interimInterests = new double[arraySize][arraySize * interestRates.length];

                                    calculateInterimInerests(interimInterests, interestRates, periodYears, periodicly, amount);
                                    printInterimInerests(interimInterests, interestRates, periodYears, periodicly);

                                    break;

                                } else
                                    System.out.println("Invalid input!!! Try again!");
                            }
                        }
                        break;
                    } catch (InputMismatchException e) {
                        //"integral"?
                        System.out.println("Need number integral! Try again!");
                        scanner.next();
                    }
                }
            }
            break;
        }

    }


    private static void printInterimInerests(double[][] interimInterests, double[] interestRates, int periodYears, double periodicly) {

        for (int i = 0; i < interestRates.length; i++) {
            System.out.print(interestRates[i] + "%: ");
            for (int j = 0; j < periodYears * periodicly; j++) {
                System.out.printf("%.2f  %.1s", interimInterests[j][i], "   ");
            }
            System.out.println();
        }
    }

    //I don't think it's a best idea to pass an empty "interimInterests" matrix as a parameter.
    //It would be better to create it inside this method.
    //See how "calculateInterimInerests" has a warning on the method name, so even Intellij is confused :)
    private static double[][] calculateInterimInerests(double[][] interimInterests, double[] interestRates, int periodYears, double periodicly, double amount) {

        //"totalAmountOneLess" variable is redundant. It can be removed.
        double totalAmountOneLess;
        double totalAmount = 0;

        for (int j = 0; j < interestRates.length; j++) {
            for (int i = 1; i < periodYears * periodicly + 1; i++) {

                totalAmount = interestCalculatorPeriod(i, amount, interestRates[j], periodicly);
                totalAmountOneLess = interestCalculatorPeriod(i - 1, amount, interestRates[j], periodicly);
                double tempAmountOneLess = totalAmount - totalAmountOneLess;
                //  interimInterest[i - 1] = tempAmountOneLess;
                interimInterests[i - 1][j] = tempAmountOneLess;
            }

            System.out.printf("Total: " + "%.2f\n", totalAmount + amount);
        }

        return interimInterests;
    }

    private static double interestCalculatorPeriod(int periodYears, double amount, double interestRate, double periodicly) {

        return amount * Math.pow(1 + (interestRate / 100) / periodicly, periodYears) - amount;
    }

    //Task 3
    private static double interestCalculatorYear(int periodYears, double amount, double interestRate, double periodicly) {

        return amount * Math.pow(1 + (interestRate / 100) / periodicly, periodYears * periodicly) - amount;
    }

    private static double switchFrequency(char period) {

        switch (period) {
            case 'D':
                return 365;
            case 'W':
                return 52;
            case 'M':
                return 12;
            case 'Q':
                return 4;
            case 'H':
                return 2;
            //"case 'Y':" and "default:" returns same result, so you can either remove last "return" and put "default:" above
            // "case 'Y':", or remove "case 'Y':" block altogether.
            case 'Y':
                return 1;
            default:
                return 1;

        }

    }

}
