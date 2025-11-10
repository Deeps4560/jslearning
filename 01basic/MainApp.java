package com.app;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainApp {

    Customer mObject;
    BankOperation mOperations;
    BufferedReader buff;
    InputStreamReader isr;
    String operation, choice;
    String bankName = "";

    public MainApp() {
        isr = new InputStreamReader(System.in);
        buff = new BufferedReader(isr);
    }

    public static void main(String[] args) {
        MainApp myObj = new MainApp();
        myObj.mOperations = new BankOperation(myObj.buff);

        System.out.println("Welcome to Indian Banking System ... IBS");
        System.out.println("Please select your bank");
        System.out.println("1. ICICI\n2. HDFC\n3. HSBC\n4. AXIS\n5. SBI");

        try {
            myObj.choice = myObj.buff.readLine();

            switch (Integer.parseInt(myObj.choice)) {
                case 1 -> myObj.bankName = "ICICI";
                case 2 -> myObj.bankName = "HDFC";
                case 3 -> myObj.bankName = "HSBC";
                case 4 -> myObj.bankName = "AXIS";
                case 5 -> myObj.bankName = "SBI";
                default -> {
                    System.out.println("❌ No valid bank selected. Exiting...");
                    return;
                }
            }

            System.out.println("\n🏦 " + myObj.bankName + " selected!!");

            while (true) {
                System.out.println("\nPlease select your Operation:");
                System.out.println("""
                        1. Create Account
                        2. Deposit Money
                        3. Withdraw Money
                        4. Open Fixed Deposit (FD)
                        5. Apply for Loan
                        6. View Customer Details
                        7. Exit""");

                myObj.operation = myObj.buff.readLine();
                int op;
                try {
                    op = Integer.parseInt(myObj.operation);
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Invalid input! Please enter a valid number.");
                    continue;
                }

                switch (op) {
                    case 1 -> myObj.mOperations.createAccount(myObj.bankName);
                    case 2 -> myObj.mOperations.depositMoney();
                    case 3 -> myObj.mOperations.withdrawMoney();
                    case 4 -> myObj.mOperations.openFD();
                    case 5 -> myObj.mOperations.applyLoan();
                    case 6 -> myObj.mOperations.showCustomerDetails();
                    case 7 -> {
                        System.out.println("🙏 Thank you for using IBS. Goodbye!");
                        return;
                    }
                    default -> System.out.println("⚠️ Invalid operation selected! Please try again.");
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("⚠️ Something went wrong. Please restart the program.");
        }
    }
}
