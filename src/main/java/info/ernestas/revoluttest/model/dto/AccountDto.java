package info.ernestas.revoluttest.model.dto;

import info.ernestas.revoluttest.model.Account;

public class AccountDto {

    private final String name;
    private final String accountNumber;
    private final double balance;

    public AccountDto() {
        name = null;
        accountNumber = null;
        balance = 0.0;
    }

    public AccountDto(String name, String accountNumber, double balance) {
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

    public static AccountDto from(Account account) {
        return new AccountDto(account.getName(), account.getAccountNumber(), account.getBalance());
    }
}
