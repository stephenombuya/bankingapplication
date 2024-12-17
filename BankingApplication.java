package bankingApplication;

import java.util.Scanner;

public class BankingApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        System.out.print("Enter customer ID: ");
        String id = scanner.nextLine();

        BankAccount account = new BankAccount(name, id);
        account.showMenu();
    }
}
