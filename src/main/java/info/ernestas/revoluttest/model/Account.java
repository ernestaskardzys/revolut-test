package info.ernestas.revoluttest.model;

public class Account {

    private final String name;
    private final String accountNumber;
    private final double balance;

    public Account() {
        name = null;
        accountNumber = null;
        balance = 0.0;
    }

    public Account(String name, String accountNumber, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public static Account from(Account account, double balance) {
        return new Account(account.getName(), account.getAccountNumber(), balance);
    }
}
