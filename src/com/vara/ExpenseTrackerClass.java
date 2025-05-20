package com.vara;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpenseTrackerClass {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n===== Expense Tracker =====");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Monthly Summary");
            System.out.println("4. Load Transactions from File");
            System.out.println("5. Save Transactions to File");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            switch (scanner.nextInt()) {
                case 1 -> addTransaction("INCOME");
                case 2 -> addTransaction("EXPENSE");
                case 3 -> viewMonthlySummary();
                case 4 -> loadFromFile();
                case 5 -> saveToFile();
                case 6 -> {
                	exit = true;
                	System.out.println("Exited from the tracker.");
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void addTransaction(String type) {
        scanner.nextLine(); // consume newline
        System.out.print("Enter date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateStr);

        System.out.print("Enter category" + 
            (type.equals("INCOME") ? " (Salary/Business): " : " (Food/Rent/Travel): "));
        String category = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        transactions.add(new Transaction(type, date, category, amount));
        System.out.println("Transaction added.");
    }

    private static void viewMonthlySummary() {
        System.out.print("Enter month (YYYY-MM): ");
        String monthInput = scanner.next();
        double incomeTotal = 0, expenseTotal = 0;

        System.out.println("\n--- Monthly Summary for " + monthInput + " ---");
        for (Transaction t : transactions) {
            String month = t.getDate().toString().substring(0, 7);
            if (month.equals(monthInput)) {
                if (t.getType().equals("INCOME")) {
                    incomeTotal += t.getAmount();
                } else if (t.getType().equals("EXPENSE")) {
                    expenseTotal += t.getAmount();
                }
            }
        }

        System.out.printf("Total Income: %.2f\n", incomeTotal);
        System.out.printf("Total Expenses: %.2f\n", expenseTotal);
        System.out.printf("Net Savings: %.2f\n", incomeTotal - expenseTotal);
    }

    private static void saveToFile() {
        System.out.print("Enter filename to save: ");
        scanner.nextLine();
        String fileName = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Transaction t : transactions) {
                writer.write(t.toFileString());
                writer.newLine();
            }
            System.out.println("Transactions saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        System.out.print("Enter filename to load: ");
        scanner.nextLine();
        String fileName = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                transactions.add(Transaction.fromFileString(line));
                count++;
            }
            System.out.println(count + " transactions loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
