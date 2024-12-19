package bankingApplication;

import java.awt.GridLayout;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class BankAccountGUI {
    private JFrame frame;
    private JTextField usernameField, passwordField, amountField;
    private JLabel balanceLabel;
    private JButton loginButton, logoutButton, depositButton, withdrawButton, balanceButton, transactionsButton;
    private int accountId = -1 ;

    public BankAccountGUI() {
        showLoginScreen();
    }

    private void showLoginScreen() {
        frame = new JFrame("Banking Application - Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        loginButton.addActionListener(e -> login());

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(new JLabel());
        frame.add(loginButton);

        frame.setVisible(true);
    }

    private void showRegisterScreen() {
        frame.dispose();
        frame = new JFrame("Banking Application - Register");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JTextField registerUsernameField = new JTextField();
        JPasswordField registerPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        JButton registerButton = new JButton("Register");

        registerButton.addActionListener(e -> registerUser(registerUsernameField.getText(),
                new String(registerPasswordField.getPassword()), new String(confirmPasswordField.getPassword())));

        frame.add(usernameLabel);
        frame.add(registerUsernameField);
        frame.add(passwordLabel);
        frame.add(registerPasswordField);
        frame.add(confirmPasswordLabel);
        frame.add(confirmPasswordField);
        frame.add(new JLabel());
        frame.add(registerButton);

        frame.setVisible(true);
    }

   
    private void registerUser(String username, String password, String confirmPassword) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(frame, "Passwords do not match.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if the username already exists
            PreparedStatement checkPs = conn.prepareStatement("SELECT * FROM Users WHERE username = ?");
            checkPs.setString(1, username);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(frame, "Username already exists.");
                return;
            }

            // Insert new user into the database and request generated keys
            String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
            PreparedStatement insertPs = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertPs.setString(1, username);
            insertPs.setString(2, password);
            int rowsAffected = insertPs.executeUpdate();

            if (rowsAffected > 0) {
                // Get the generated userId
                ResultSet generatedKeys = insertPs.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1); // Get the generated userId
                    createAccount(userId, 0.00); // Create an account for the new user
                }

                JOptionPane.showMessageDialog(frame, "Registration successful! Please log in.");
                showLoginScreen(); // Redirect to login page after successful registration
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error connecting to database.");
        }
    }


    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?")) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                accountId = rs.getInt("userId");
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error connecting to database.");
        }
    }

    private void showMainMenu() {
        frame.dispose();
        frame = new JFrame("Banking Application - Main Menu");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1, 10, 10));

        balanceLabel = new JLabel("Balance: $0.00", SwingConstants.CENTER);
        frame.add(balanceLabel);

        amountField = new JTextField();
        amountField.setHorizontalAlignment(JTextField.CENTER);
        amountField.setToolTipText("Enter amount here");
        frame.add(amountField);

        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        balanceButton = new JButton("Check Balance");
        transactionsButton = new JButton("View Transactions");
        logoutButton = new JButton("Logout");

        depositButton.addActionListener(e -> deposit());
        withdrawButton.addActionListener(e -> withdraw());
        balanceButton.addActionListener(e -> checkBalance());
        transactionsButton.addActionListener(e -> viewTransactions());
        logoutButton.addActionListener(e -> logout());

        frame.add(depositButton);
        frame.add(withdrawButton);
        frame.add(balanceButton);
        frame.add(transactionsButton);
        frame.add(logoutButton);

        frame.setVisible(true);
        checkBalance();
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) throw new NumberFormatException();

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE Accounts SET balance = balance + ? WHERE userId = ?")) {

                ps.setDouble(1, amount);
                ps.setInt(2, accountId);
                ps.executeUpdate();

                recordTransaction("DEPOSIT", amount);
                JOptionPane.showMessageDialog(frame, "Deposited: $" + amount);
                checkBalance();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid amount. Please enter a positive number.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        amountField.setText("");
    }

    private void withdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) throw new NumberFormatException();

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE Accounts SET balance = balance - ? WHERE userId = ? AND balance >= ?")) {

                ps.setDouble(1, amount);
                ps.setInt(2, accountId);
                ps.setDouble(3, amount);
                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    recordTransaction("WITHDRAW", amount);
                    JOptionPane.showMessageDialog(frame, "Withdrawn: $" + amount);
                    checkBalance();
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient balance.");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid amount. Please enter a positive number.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        amountField.setText("");
    }

    private void checkBalance() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT balance FROM Accounts WHERE userId = ?")) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                balanceLabel.setText("Balance: $" + balance);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void viewTransactions() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Transactions WHERE accountId = ? ORDER BY timestamp DESC")) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            ArrayList<String> transactions = new ArrayList<>();

            while (rs.next()) {
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                String timestamp = rs.getString("timestamp");
                transactions.add(String.format("%s: $%.2f on %s", type, amount, timestamp));
            }

            if (transactions.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No transactions found.");
            } else {
                JOptionPane.showMessageDialog(frame, String.join("\n", transactions));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error retrieving transactions.");
        }
    }

    private void recordTransaction(String type, double amount) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if the accountId exists in the Accounts table
            PreparedStatement checkAccountPs = conn.prepareStatement("SELECT * FROM Accounts WHERE accountId = ?");
            checkAccountPs.setInt(1, accountId);
            ResultSet rs = checkAccountPs.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(frame, "Account ID does not exist.");
                return;
            }

            // Proceed to insert the transaction if the accountId exists
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Transactions (accountId, type, amount) VALUES (?, ?, ?)");
            ps.setInt(1, accountId);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void logout() {
        frame.dispose();
        showLoginScreen();
    }
    
   
    
    private void createAccount(int userId, double initialBalance) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Prepare SQL query to insert new account into Accounts table
            String sql = "INSERT INTO Accounts (userId, balance) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            // Set parameters for the account details
            ps.setInt(1, userId);  // Set the userId from the registered user
            ps.setDouble(2, initialBalance);  // Set the initial balance
            
            // Execute the insert statement
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(frame, "Account created successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Error creating account.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred while creating the account.");
        }
    }


    public static void main(String[] args) {
        BankAccountGUI app = new BankAccountGUI();
        int option = JOptionPane.showOptionDialog(null, "Would you like to log in or register?", "Banking Application",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Login", "Register" }, "Login");

        if (option == JOptionPane.YES_OPTION) {
            app.showLoginScreen();
        } else {
            app.showRegisterScreen();
        }
    }
}
