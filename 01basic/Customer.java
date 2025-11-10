package com.app;



public class Customer {

    private String custName;
    private String custPhone;
    private String bankName;
    private int accountNumber;
    private double balance;

    private static int accountCounter = 1000; // Auto-generate account numbers

    public Customer() {}

    public Customer(String custName, String custPhone, String bankName) {
        this.custName = custName;
        this.custPhone = custPhone;
        this.bankName = bankName;
        this.accountNumber = ++accountCounter;
        this.balance = 0.0;
    }

    public String getCustName() { return custName; }
    public void setCustName(String custName) { this.custName = custName; }

    public String getCustPhone() { return custPhone; }
    public void setCustPhone(String custPhone) { this.custPhone = custPhone; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public int getAccountNumber() { return accountNumber; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public void displayDetails() {
        System.out.println("\n=== Customer Details ===");
        System.out.println("Bank Name      : " + bankName);
        System.out.println("Customer Name  : " + custName);
        System.out.println("Phone Number   : " + custPhone);
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Current Balance: ₹" + balance);
        System.out.println("==========================\n");
    }
}
