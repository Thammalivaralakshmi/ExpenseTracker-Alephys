package com.vara;
import java.time.LocalDate;

class Transaction {
    private String type; // "INCOME" or "EXPENSE"
    private LocalDate date;
    private String category;
    private double amount;

    public Transaction(String type, LocalDate date, String category, double amount) {
        this.type = type;
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public String getType() { return type; }
    public LocalDate getDate() { return date; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }

    public String toFileString() {
        return type + "," + date + "," + category + "," + amount;
    }

    public static Transaction fromFileString(String line) {
        String[] parts = line.split(",");
        String type = parts[0];
        LocalDate date = LocalDate.parse(parts[1]);
        String category = parts[2];
        double amount = Double.parseDouble(parts[3]);
        return new Transaction(type, date, category, amount);
    }
}