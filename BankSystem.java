package com.mycompany.banksystem;

import java.util.Scanner;

/**
 * A simple console banking simulation (coursework).
 * Demo data only: no real accounts, names or money.
 */
public class BankSystem {

    // One Scanner for the whole program (creating several on System.in causes problems)
    static Scanner input = new Scanner(System.in);

    // Demo account details
    static String accountHolder = "Khloe Khumalo";
    static String accountNumber = "5767984765";   // String, because 10 digits is too big for an int
    static String pin = "1234";                   // demo PIN, stored as text so a leading 0 is kept
    static double balance = 5000.00;
    static double savingsBalance = 0.00;

    static final double MIN_SEND = 40.00;
    static final double MAX_SEND = 3000.00;
    static final int MAX_PIN_ATTEMPTS = 3;

    // ---------- Helper methods ----------

    // Keeps asking until the user types a whole number (no crash on letters)
    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = input.nextLine().trim();
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    // Keeps asking until the user types an amount greater than 0
    static double readAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = input.nextLine().trim();
            try {
                double value = Double.parseDouble(text);
                if (value > 0) {
                    return value;
                }
                System.out.println("The amount must be more than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount, for example 250 or 99.50");
            }
        }
    }

    // Keeps asking until the user types exactly the number of digits asked for
    static String readDigits(String prompt, int length) {
        while (true) {
            System.out.print(prompt);
            String text = input.nextLine().trim();
            if (text.matches("\\d{" + length + "}")) {
                return text;
            }
            System.out.println("Please enter exactly " + length + " digits.");
        }
    }

    // Checks the PIN. The user gets a limited number of attempts.
    static boolean confirmPin() {
        for (int attempt = 1; attempt <= MAX_PIN_ATTEMPTS; attempt++) {
            System.out.print("Enter your PIN: ");
            String entered = input.nextLine().trim();
            if (entered.equals(pin)) {
                return true;
            }
            System.out.println("Wrong PIN. Attempts left: " + (MAX_PIN_ATTEMPTS - attempt));
        }
        System.out.println("Too many wrong attempts.");
        return false;
    }

    static void showAccountDetails() {
        System.out.println("ACCOUNT DETAILS");
        System.out.println("---------------------------");
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.printf("Available Balance: R%.2f%n", balance);
    }

    // ---------- Menus ----------

    // Returns true if the user is logged in (or registered), false otherwise
    public static boolean menu() {
        System.out.println("WELCOME TO DEMO BANK");
        System.out.println("Enter 1 to Login");
        System.out.println("Enter 2 to Register Account");
        int choice = readInt("Choice: ");

        if (choice == 1) {
            if (!confirmPin()) {
                return false;
            }
            showAccountDetails();
            return true;
        } else if (choice == 2) {
            registerAccount();
            return true;
        } else {
            System.out.println("Invalid option.");
            return false;
        }
    }

    static void registerAccount() {
        System.out.println("You are registering an account");
        System.out.println("-----------------------------");

        String username = "";
        while (username.isEmpty()) {
            System.out.print("Enter a username: ");
            username = input.nextLine().trim();
        }

        String newPin = readDigits("Create a 4-digit PIN: ", 4);

        // The new details replace the demo account, so they are really used afterwards
        accountHolder = username;
        pin = newPin;
        accountNumber = String.valueOf(1000000000L + (long) (Math.random() * 9000000000L));
        balance = 0.00;
        savingsBalance = 0.00;

        System.out.println("Account created successfully. Please see your account details below.");
        showAccountDetails();
    }

    public static void transactionMenu() {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("OPTIONS:");
            System.out.println("----------------------");
            System.out.println("1 Deposit");
            System.out.println("2 Withdraw");
            System.out.println("3 Transfer");
            System.out.println("4 Send Cash");
            System.out.println("5 Exit");
            int choice = readInt("Choice: ");

            switch (choice) {
                case 1:
                    deposit();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    transfer();
                    break;
                case 4:
                    sendCash();
                    break;
                case 5:
                    running = false;
                    System.out.println("Thank you for banking with us.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ---------- Transactions ----------

    static void deposit() {
        double amount = readAmount("Enter the amount you wish to deposit: ");
        balance += amount;
        System.out.println("Deposit successful.");
        System.out.printf("Your current balance: R%.2f%n", balance);
    }

    static void withdraw() {
        double amount = readAmount("Withdrawal amount: ");
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }
        balance -= amount;
        System.out.printf("You have withdrawn R%.2f%n", amount);
        System.out.printf("Your current balance: R%.2f%n", balance);
    }

    static void transfer() {
        System.out.println("Transfer Money");
        System.out.println("Enter 1 to transfer to an account");
        System.out.println("Enter 2 to transfer to your savings");
        int choice = readInt("Choice: ");

        if (choice == 1) {
            transferToAccount();
        } else if (choice == 2) {
            transferToSavings();
        } else {
            System.out.println("Invalid option.");
        }
    }

    static void transferToAccount() {
        String toAccount = readDigits("Enter the 10-digit account number: ", 10);
        if (toAccount.equals(accountNumber)) {
            System.out.println("You cannot transfer to your own account.");
            return;
        }

        double amount = readAmount("Enter amount: ");
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }

        System.out.printf("You are transferring R%.2f to %s.%n", amount, toAccount);
        if (!confirmPin()) {
            System.out.println("Transfer cancelled.");
            return;
        }

        balance -= amount;
        System.out.println("Transfer successful.");
        System.out.printf("Current balance: R%.2f%n", balance);
    }

    static void transferToSavings() {
        double amount = readAmount("Enter amount: ");
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }

        balance -= amount;
        savingsBalance += amount;
        System.out.printf("You transferred R%.2f to your savings.%n", amount);
        System.out.printf("Current balance: R%.2f%n", balance);
        System.out.printf("Current savings balance: R%.2f%n", savingsBalance);
    }

    static void sendCash() {
        System.out.println("Send Cash");
        System.out.printf("Amount R%.0f - R%.0f%n", MIN_SEND, MAX_SEND);
        double amount = readAmount("Enter amount: ");

        if (amount < MIN_SEND || amount > MAX_SEND) {
            System.out.printf("The amount must be between R%.0f and R%.0f.%n", MIN_SEND, MAX_SEND);
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }

        String secretCode = readDigits("Create a 4-digit secret code: ", 4);
        System.out.printf("You are sending R%.2f. Enter your PIN to proceed.%n", amount);
        if (!confirmPin()) {
            System.out.println("Cash send cancelled.");
            return;
        }

        balance -= amount;
        System.out.println("Cash send transaction successful.");
        System.out.println("Send the secret code to the recipient in a separate message so they can collect the cash.");
        System.out.println();
        System.out.println("HISTORY");
        System.out.println("==========================================");
        System.out.println("Transaction type: Cash sent");
        System.out.printf("Amount: R%.2f%n", amount);
        System.out.println("From account: " + accountNumber);
        System.out.printf("Current balance: R%.2f%n", balance);
    }

    public static void main(String[] args) {
        if (menu()) {
            transactionMenu();
        }
    }
}
