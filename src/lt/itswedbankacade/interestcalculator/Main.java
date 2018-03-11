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
        while (true) {

            System.out.printf("Amount: ");
            double amount = scanner.nextDouble();
            if (amount <= 0) System.out.println("Invalid input! Try again!");
            else {

                while (true) {
                    System.out.printf("Period years: ");
                    try {
                        int periodYears = scanner.nextInt();
                        if (periodYears <= 0) System.out.println("Invalid input! Try again!");
                        else {

                            double[] newArr = new double[10000];
                            double[] interestRates;
                            int indexSize = 0;

                            do {

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
                                System.out.printf("Compound frequency: ");
                                String character = scanner.next();
                                char period = character.charAt(0);

                                if (period == 'Y' || period == 'D' || period == 'W' || period == 'Q' || period == 'H' || period == 'M') {

                                    double periodicly;
                                    periodicly = switchFrequency(period);

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

    private static double[][] calculateInterimInerests(double[][] interimInterests, double[] interestRates, int periodYears, double periodicly, double amount) {

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
            case 'Y':
                return 1;
            default:
                return 1;

        }

    }

}
