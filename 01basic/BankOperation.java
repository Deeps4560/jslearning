package com.app;

import java.io.BufferedReader;
import java.io.IOException;

public class BankOperation {
    private static final double MIN_BALANCE = 5000.0;

    private final BufferedReader buff;
    private Customer customer;

    private int failedAttempts = 0;   // count of failed withdrawals
    private boolean isBlocked = false;

    public BankOperation(BufferedReader buff) {
        this.buff = buff;
    }

    // ---------- Create Account ----------
    public void createAccount(String bankName) {
        try {
            System.out.print("Enter Customer Name: ");
            String name = buff.readLine();

            System.out.print("Enter Customer Phone: ");
            String phone = buff.readLine();

            customer = new Customer(name, phone, bankName);
            failedAttempts = 0;
            isBlocked = false;

            System.out.println("\n✅ Account created successfully!");
            customer.displayDetails();

        } catch (IOException e) {
            System.out.println("Error while creating account: " + e.getMessage());
        }
    }

    // ---------- Deposit Money ----------
    public void depositMoney() {
        if (customer == null) {
            System.out.println("⚠️ Please create an account first!");
            return;
        }

        try {
            System.out.print("Enter deposit amount: ₹");
            double amt = Double.parseDouble(buff.readLine());
            if (amt <= 0) {
                System.out.println("❌ Invalid amount!");
                return;
            }

            customer.setBalance(customer.getBalance() + amt);
            System.out.println("✅ ₹" + amt + " deposited successfully!");
            System.out.println("Updated Balance: ₹" + customer.getBalance());

            // Auto-unblock if balance becomes healthy
            if (isBlocked && customer.getBalance() >= MIN_BALANCE) {
                isBlocked = false;
                failedAttempts = 0;
                System.out.println("✅ Account reactivated (balance ≥ ₹" + (int) MIN_BALANCE + ").");
            }
            System.out.println();

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error while depositing money.");
        }
    }

    // ---------- Withdraw Money ----------
    public void withdrawMoney() {
        if (customer == null) {
            System.out.println("⚠️ Please create an account first!");
            return;
        }

        if (isBlocked) {
            System.out.println("🚫 Account is BLOCKED. Deposit funds (balance ≥ ₹" + (int) MIN_BALANCE + ") to reactivate.");
            return;
        }

        double balance = customer.getBalance();

        try {
            System.out.print("Enter withdrawal amount: ₹");
            double amt = Double.parseDouble(buff.readLine());

            if (amt <= 0) {
                System.out.println("❌ Invalid amount!");
                return;
            }

            // 🔴 CASE 1: Current balance already below ₹5000
            if (balance < MIN_BALANCE) {
                failedAttempts++;
                rejectDueToLowBalance(balance);
                return;
            }

            // 🔴 CASE 2: Check if withdrawal amount is higher than available balance
            if (amt > balance) {
                System.out.println("⚠️ Insufficient balance! Current: ₹" + balance);
                return;
            }

            // 🔴 CASE 3: Check if final balance will fall below ₹5000 (NEW RULE)
            double finalBalance = balance - amt;
            if (finalBalance < MIN_BALANCE) {
                failedAttempts++;
                if (failedAttempts >= 3) {
                    isBlocked = true;
                    System.out.println("🚫 Withdrawal rejected! This transaction would reduce balance to ₹" + finalBalance +
                            " (< ₹" + (int) MIN_BALANCE + ").");
                    System.out.println("🚫 Account BLOCKED after 3 failed attempts.");
                } else {
                    System.out.println("⚠️ Withdrawal rejected! This transaction would reduce balance to ₹" + finalBalance +
                            " (< ₹" + (int) MIN_BALANCE + ").");
                    System.out.println("Attempt " + failedAttempts + " of 3\n");
                }
                return;
            }

            // 🟢 CASE 4: If all checks passed, perform withdrawal
            customer.setBalance(finalBalance);
            System.out.println("✅ Transaction successful! ₹" + amt + " withdrawn.");
            System.out.println("Remaining Balance: ₹" + customer.getBalance() + "\n");

            // Reset failed attempts on success
            failedAttempts = 0;

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error while withdrawing money.");
        }
    }


    // ---------- Helper Methods ----------
    private void rejectDueToLowBalance(double balance) {
        if (failedAttempts >= 3) {
            isBlocked = true;
            System.out.println("🚫 Withdraw rejected! Balance ₹" + balance + " is below ₹" + (int) MIN_BALANCE + ".");
            System.out.println("🚫 Account BLOCKED after 3 failed attempts.");
        } else {
            System.out.println("⚠️ Withdraw rejected! Balance ₹" + balance + " is below ₹" + (int) MIN_BALANCE + ".");
            System.out.println("Attempt " + failedAttempts + " of 3\n");
        }
    }

    private void rejectDueToFinalBalance(double balance, double amt, double finalBalance) {
        if (failedAttempts >= 3) {
            isBlocked = true;
            System.out.println("🚫 Withdrawal rejected! This transaction would reduce balance to ₹" +
                    finalBalance + " (< ₹" + (int) MIN_BALANCE + ").");
            System.out.println("🚫 Account BLOCKED after 3 failed attempts.");
        } else {
            System.out.println("⚠️ Withdrawal rejected! This transaction would reduce balance to ₹" +
                    finalBalance + " (< ₹" + (int) MIN_BALANCE + ").");
            System.out.println("Attempt " + failedAttempts + " of 3\n");
        }
    }

    // ---------- Other Operations ----------
    public void openFD() {
        System.out.println("💰 Fixed Deposit feature coming soon!");
    }

    public void applyLoan() {
        System.out.println("🏦 Loan application feature coming soon!");
    }

    public void showCustomerDetails() {
        if (customer == null) {
            System.out.println("⚠️ No customer account found. Please create an account first!");
        } else {
            customer.displayDetails();
            System.out.println("Account Status  : " + (isBlocked ? "🚫 BLOCKED" : "✅ ACTIVE"));
            System.out.println("Failed Attempts : " + failedAttempts + "\n");
        }
    }
}
