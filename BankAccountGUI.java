package bankingApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankAccountGUI {
    private int balance = 0;
    private int previousTransaction = 0;
    private final String customerName;
    private final String customerId;

    // GUI Components
    private JFrame frame;
    private JLabel welcomeLabel, balanceLabel, transactionLabel;
    private JTextField amountField;
    private JButton depositButton, withdrawButton, checkBalanceButton, transactionButton, exitButton;

    public BankAccountGUI(String customerName, String customerId) {
        this.customerName = customerName;
        this.customerId = customerId;

        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Banking Application");
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(7, 1, 10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Welcome Label
        welcomeLabel = new JLabel("Welcome " + customerName + " (ID: " + customerId + ")", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(welcomeLabel);

        // Balance Display Label
        balanceLabel = new JLabel("Balance: $" + balance, SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(balanceLabel);

        // Amount Input Field
        amountField = new JTextField();
        amountField.setHorizontalAlignment(JTextField.CENTER);
        amountField.setToolTipText("Enter amount here");
        frame.add(amountField);

        // Buttons
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        checkBalanceButton = new JButton("Check Balance");
        transactionButton = new JButton("Previous Transaction");
        exitButton = new JButton("Exit");

        frame.add(depositButton);
        frame.add(withdrawButton);
        frame.add(checkBalanceButton);
        frame.add(transactionButton);
        frame.add(exitButton);

        // Event Handling
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        transactionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPreviousTransaction();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Thank you for using our services!");
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    private void deposit() {
        try {
            int amount = Integer.parseInt(amountField.getText());
            if (amount > 0) {
                balance += amount;
                previousTransaction = amount;
                balanceLabel.setText("Balance: $" + balance);
                JOptionPane.showMessageDialog(frame, "Deposited: $" + amount);
            } else {
                JOptionPane.showMessageDialog(frame, "Amount must be positive!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number.");
        }
        amountField.setText("");
    }

    private void withdraw() {
        try {
            int amount = Integer.parseInt(amountField.getText());
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                previousTransaction = -amount;
                balanceLabel.setText("Balance: $" + balance);
                JOptionPane.showMessageDialog(frame, "Withdrawn: $" + amount);
            } else if (amount > balance) {
                JOptionPane.showMessageDialog(frame, "Insufficient balance.");
            } else {
                JOptionPane.showMessageDialog(frame, "Amount must be positive!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number.");
        }
        amountField.setText("");
    }

    private void checkBalance() {
        JOptionPane.showMessageDialog(frame, "Your current balance is: $" + balance);
    }

    private void showPreviousTransaction() {
        if (previousTransaction > 0) {
            JOptionPane.showMessageDialog(frame, "Last Transaction: Deposited $" + previousTransaction);
        } else if (previousTransaction < 0) {
            JOptionPane.showMessageDialog(frame, "Last Transaction: Withdrawn $" + Math.abs(previousTransaction));
        } else {
            JOptionPane.showMessageDialog(frame, "No transactions recorded.");
        }
    }

    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog("Enter your name:");
        String id = JOptionPane.showInputDialog("Enter your customer ID:");
        if (name != null && id != null) {
            new BankAccountGUI(name, id);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input. Exiting application.");
        }
    }
}
