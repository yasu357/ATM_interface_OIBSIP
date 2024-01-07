import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATMsystem {

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}

class ATM {
    private Scanner scanner;
    private Account currentUser;

    public void start() {
        scanner = new Scanner(System.in);

        System.out.println("Welcome to the ATM!");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        currentUser = authenticateUser(userId, pin);

        if (currentUser != null) {
            System.out.println("Authentication successful. Welcome, " + currentUser.getName() + "!");
            showMenu();
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }

    private Account authenticateUser(String userId, String pin) {
        if (userId.equals("yasu") && pin.equals("1234")) {
            return new Account("yasmeen taj", 1000.0);
        } else {
            return null;
        }
    }

    private void showMenu() {
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            int choice = getChoice();

            switch (choice) {
                case 1:
                    showTransactionsHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int getChoice() {
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.print("Enter your choice: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void showTransactionsHistory() {
        List<String> transactions = currentUser.getTransactionHistory();

        System.out.println("Transaction History:");
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    private void withdraw() {
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (currentUser.withdraw(amount)) {
            System.out.println("Withdrawal successful. Remaining balance: " + currentUser.getBalance());
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    private void deposit() {
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();
        currentUser.deposit(amount);
        System.out.println("Deposit successful. New balance: " + currentUser.getBalance());
    }

    private void transfer() {
        System.out.print("Enter the recipient's account number: ");
        String recipientAccountNumber = scanner.next();
        System.out.print("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();
        System.out.println("Transfer of " + amount + " to account " + recipientAccountNumber + " successful.");
    }
}

class Account {
    private String name;
    private double balance;
    private List<String> transactionHistory;

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addTransaction("Withdrawal: -$" + amount);
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("Deposit: +$" + amount);
        }
    }
}
