package info.ernestas.revoluttest.model.dto;

import info.ernestas.revoluttest.model.Account;

public class AccountResponseDto {

    private final String name;
    private final String accountNumber;
    private final double balance;

    public AccountResponseDto() {
        name = null;
        accountNumber = null;
        balance = 0.0;
    }

    public AccountResponseDto(String name, String accountNumber, double balance) {
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

    public static AccountResponseDto from(Account account) {
        return new AccountResponseDto(account.getName(), account.getAccountNumber(), account.getBalance());
    }
}
