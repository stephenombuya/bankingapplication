package bankingApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankAccountGUI {
    private final BankAccount bankAccount;

    // GUI Components
    private JFrame frame;
    private JLabel balanceLabel, transactionLabel;
    private JTextField amountField;

    public BankAccountGUI(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Banking Application");
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(6, 1, 10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome " + bankAccount.customerName + " (ID: " + bankAccount.customerId + ")", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(welcomeLabel);

        // Balance Label
        balanceLabel = new JLabel("Balance: $" + bankAccount.getBalance(), SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(balanceLabel);

        // Amount Input Field
        amountField = new JTextField();
        amountField.setHorizontalAlignment(JTextField.CENTER);
        amountField.setToolTipText("Enter amount here");
        frame.add(amountField);

        // Buttons
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transactionButton = new JButton("Previous Transaction");

        frame.add(depositButton);
        frame.add(withdrawButton);
        frame.add(transactionButton);

        // Event Handling
        depositButton.addActionListener(e -> handleDeposit());
        withdrawButton.addActionListener(e -> handleWithdraw());
        transactionButton.addActionListener(e -> showPreviousTransaction());

        frame.setVisible(true);
    }

    private void handleDeposit() {
        try {
            int amount = Integer.parseInt(amountField.getText());
            bankAccount.deposit(amount);
            balanceLabel.setText("Balance: $" + bankAccount.getBalance());
            JOptionPane.showMessageDialog(frame, "Successfully deposited: $" + amount);
        } catch (IllegalArgumentException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
        amountField.setText("");
    }

    private void handleWithdraw() {
        try {
            int amount = Integer.parseInt(amountField.getText());
            bankAccount.withdraw(amount);
            balanceLabel.setText("Balance: $" + bankAccount.getBalance());
            JOptionPane.showMessageDialog(frame, "Successfully withdrew: $" + amount);
        } catch (IllegalArgumentException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
        amountField.setText("");
    }

    private void showPreviousTransaction() {
        String transaction = bankAccount.getPreviousTransaction();
        JOptionPane.showMessageDialog(frame, transaction);
    }
}
