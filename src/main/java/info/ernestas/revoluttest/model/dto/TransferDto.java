package info.ernestas.revoluttest.model.dto;

public class TransferDto {

    private final String accountFrom;
    private final String accountTo;
    private final double amount;

    public TransferDto() {
        accountFrom = null;
        accountTo = null;
        amount = 0.0;
    }

    public TransferDto(String accountFrom, String accountTo, double amount) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public double getAmount() {
        return amount;
    }
}
