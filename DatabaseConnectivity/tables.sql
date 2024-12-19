-- Create the database
CREATE DATABASE BankApplication;

-- Use the database
USE BankApplication;
SHOW TABLES;

-- Create the users table for login credentials
CREATE TABLE Users (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Create the accounts table
CREATE TABLE Accounts (
    accountId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    balance DECIMAL(10, 2) DEFAULT 0.00,
    FOREIGN KEY (userId) REFERENCES Users(userId)
);


-- Create the transactions table
CREATE TABLE Transactions (
    transactionId INT AUTO_INCREMENT PRIMARY KEY,
    accountId INT NOT NULL,
    type ENUM('DEPOSIT', 'WITHDRAW') NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (accountId) REFERENCES Accounts(accountId)
);
