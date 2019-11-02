package info.ernestas.revoluttest.model;

public class TransferInfo {

    private final String accountFrom;
    private final String accountTo;
    private final double amount;

    public TransferInfo() {
        accountFrom = null;
        accountTo = null;
        amount = 0.0;
    }

    public TransferInfo(String accountFrom, String accountTo, double amount) {
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
