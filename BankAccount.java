package bankingApplication;

public class BankAccount {
    private double balance;
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
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
    }

    public void withdraw(int amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            previousTransaction = -amount;
        } else if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance.");
        } else {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getPreviousTransaction() {
        if (previousTransaction > 0) {
            return "Deposited: $" + previousTransaction;
        } else if (previousTransaction < 0) {
            return "Withdrew: $" + Math.abs(previousTransaction);
        } else {
            return "No transactions recorded.";
        }
    }

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerId() {
		return customerId;
	}
}
