package util;

import java.util.Scanner;

public class InputValidators {
    public static int validateChoice(Scanner scanner) {
        while (true) {
            if(!scanner.hasNextInt()) {
                System.out.println("Please enter a valid choice from 0 - 14");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            if (choice < 0 || choice > 14) {
                System.out.println("Please enter a valid choice from 0 - 14");
                continue;
            }
            return choice;
        }
    }

    public static int validateInteger (Scanner scanner, String name , int min) {
        while (true) {
            if(!scanner.hasNextInt()) {
                System.out.printf("Please enter valid %s \n",name);
                scanner.next();
                continue;
            }
            int integer = scanner.nextInt();
            if (integer < min) {
                System.out.printf("Please enter a valid %s \n",name);
                continue;
            }
            return integer;
        }
    }
    public static String validateName(Scanner scanner) {
        while (true) {
            if(!scanner.hasNextLine()) {
                System.out.println("Please enter a valid name");
                scanner.nextLine();
                continue;
            }
            String name = scanner.nextLine();
            if(name.length() < 3) {
                System.out.println("Please enter a valid name between 3 and 20 characters");
                continue;
            }
            return name;
        }
    }

    public static double validateBalance (Scanner scanner) {
        while (true) {
            if(!scanner.hasNextDouble()) {
                System.out.println("please enter a valid balance ");
                scanner.nextLine();
                continue;
            }
            double balance = scanner.nextDouble();
            if(balance < 0) {
                System.out.println("please enter a valid balance greater than 0");
                continue;
            }
            return balance;
        }
    }



}
