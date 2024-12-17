# **Banking Application**
This is a simple Banking Application that provides a user-friendly Graphical User Interface (GUI) for managing basic banking operations like deposits, withdrawals, balance checks, and viewing the previous transaction.

### **Features ✨**
- **Deposit Money**: Users can add money to their account.
- **Withdraw Money**: Users can withdraw money from their account.
- **Check Balance**: Displays the current account balance.
- **View Previous Transaction**: Shows the last transaction (Deposit/Withdrawal).
- **Exit Option**: Safely exits the application.
- **User-Friendly GUI**: The interface is intuitive, built with Java Swing.

---

### **Technologies Used 🛠️**
- **Java**: Core programming language.
- **Swing**: For building the graphical user interface.
- **JOptionPane**: For input dialogs and pop-up messages.

---

### **Screenshots 📸**
- **Welcome Screen**:
  - Users are prompted to enter their name and customer ID at the start.

- **Main GUI**:
  - Buttons to Deposit, Withdraw, Check Balance, and more.

---

### **Project Structure 📂**
The project consists of the following key files:

```
BankingApplication/
└── bankingapplication/
│       ├── BankAccount.java
│       ├── BankingApplication.java
│       ├── BankAccountGUI.java   
│
└── README.md
```

---


### **How to Run the Project ▶️**
Follow these steps to set up and run the application:

1. Prerequisites
- Java Development Kit (JDK): Version 8 or above.
- An IDE (e.g., IntelliJ IDEA, Eclipse, or NetBeans) or a terminal for compilation.

2. Clone the Repository
- If you’re using Git, clone the repository:

```bash
git clone https://github.com/stephenombuya/bankingapplication/tree/main
cd BankingApplication
```
- Alternatively, download the project as a ZIP file and extract it.

3. Compile the Project
- Open the project in your IDE or compile it manually:

```bash
javac src/bankingApplication/BankAccountGUI.java
```

4. Run the Project
Execute the compiled class:

```bash
java bankingApplication.BankAccountGUI
```

5. Follow the Instructions
- Enter your name and customer ID when prompted.
- Use the buttons in the GUI to perform banking operations.

---

### **Usage Guide 📘**
1. **Interface Components**:
  - **Welcome Label**: Displays the user’s name and ID.
  - **Text Field**: Enter the amount for deposits/withdrawals.
2. **Buttons**:
  - **Deposit**: Adds the entered amount to your balance.
  - **Withdraw**: Deducts the entered amount (if sufficient) from your balance.
  - **Check Balance**: Displays the current balance in a dialog box.
  - **Previous Transaction**: Shows the most recent transaction.
  - **Exit**: Closes the application safely.

---

### **Error Handling 🛡️**
The application includes error handling for:

  - Invalid inputs (e.g., non-numeric amounts).
  - Withdrawals exceeding the available balance.
  - Negative amounts for deposits or withdrawals.

---

### **Future Improvements 🚧**
- Add file storage to save transaction history persistently.
- Implement user authentication with a login screen.
- Support for multiple accounts in the same session.
- Use a database (e.g., MySQL) to store user data securely.


### **Contributing 🤝**
Contributions are welcome! If you want to improve this project:

1. Fork the repository.
2. Create a new branch (feature-branch).
3. Commit your changes and submit a Pull Request.

---

### **License 📝**
This project is licensed under the Apache 2.0 License. You are free to use, modify, and distribute it as long as proper credit is given.

---

Enjoy using the Banking Application! If you face any issues, feel free to open an issue in the repository. 😊
