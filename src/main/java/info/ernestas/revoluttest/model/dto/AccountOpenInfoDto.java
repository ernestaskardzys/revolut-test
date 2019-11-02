package info.ernestas.revoluttest.model.dto;

public class AccountOpenInfoDto {

    private final String name;

    public AccountOpenInfoDto() {
        name = null;
    }

    public AccountOpenInfoDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
