package info.ernestas.revoluttest.model.dto;

public class AccountOpenDto {

    private final String name;

    public AccountOpenDto() {
        name = null;
    }

    public AccountOpenDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
