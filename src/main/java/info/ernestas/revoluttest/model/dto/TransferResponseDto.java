package info.ernestas.revoluttest.model.dto;

public class TransferResponseDto {

    private final double amount;
    private final boolean transferred;

    public TransferResponseDto() {
        amount = 0.0;
        transferred = false;
    }

    public TransferResponseDto(double amount, boolean transferred) {
        this.amount = amount;
        this.transferred = transferred;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isTransferred() {
        return transferred;
    }

    public static TransferResponseDto from(TransferDto transferDto) {
        return new TransferResponseDto(transferDto.getAmount(), true);
    }

}
