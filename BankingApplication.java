package bankingApplication;

public class BankingApplication {
    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog("Enter your name:");
        String id = JOptionPane.showInputDialog("Enter your customer ID:");
        if (name != null && id != null && !name.isEmpty() && !id.isEmpty()) {
            BankAccount bankAccount = new BankAccount(name, id);
            new BankAccountGUI(bankAccount);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input. Exiting application.");
        }
    }
}
