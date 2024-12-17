package bankingApplication;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BankAccount {
    private int balance;
    private int previousTransaction;
    private final String customerName;
    private final String customerId;

    public BankAccount(String customerName, String customerId) {
        this.customerName = customerName;
        this.customerId = customerId;
    }

    public void deposit(int amount) {
        if (amount > 0) {
            balance += amount;
            previousTransaction = amount;
            System.out.println("Successfully deposited: " + amount);
        } else {
            System.out.println("Invalid amount. Deposit must be positive!");
        }
    }

    public void withdraw(int amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            previousTransaction = -amount;
            System.out.println("Successfully withdrew: " + amount);
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            System.out.println("Invalid amount. Withdrawal must be positive!");
        }
    }

    public void getPreviousTransaction() {
        if (previousTransaction > 0) {
            System.out.println("Last Transaction: Deposited " + previousTransaction);
        } else if (previousTransaction < 0) {
            System.out.println("Last Transaction: Withdrew " + Math.abs(previousTransaction));
        } else {
            System.out.println("No transactions recorded.");
        }
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        char option;

        do {
            displayMenu();
            option = scanner.next().charAt(0);
            scanner.nextLine();

            switch (Character.toUpperCase(option)) {
                case 'A' -> System.out.println("Current Balance: " + balance);
                case 'B' -> {
                    System.out.print("Enter deposit amount: ");
                    handleInput(() -> deposit(scanner.nextInt()), scanner);
                }
                case 'C' -> {
                    System.out.print("Enter withdrawal amount: ");
                    handleInput(() -> withdraw(scanner.nextInt()), scanner);
                }
                case 'D' -> getPreviousTransaction();
                case 'E' -> System.out.println("Thank you for banking with us!");
                default -> System.out.println("Invalid option! Please try again.");
            }
        } while (option != 'E');
    }

    private void displayMenu() {
        System.out.println("\n============== MENU ==============");
        System.out.println("A: Check Balance");
        System.out.println("B: Deposit");
        System.out.println("C: Withdraw");
        System.out.println("D: Previous Transaction");
        System.out.println("E: Exit");
        System.out.print("Choose an option: ");
    }

    private void handleInput(Runnable action, Scanner scanner) {
        try {
            action.run();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine();
        }
    }
}
