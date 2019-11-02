package info.ernestas.revoluttest.data;

import info.ernestas.revoluttest.model.Account;

public class AccountTestData {

    public static Account getJohnDoeAccount() {
        return new Account("John Doe", "54b0c38c723c7852334526fd0127bc165ea0b9d9b4034bb6b93195704766b677", 100.0);
    }

    public static Account getJaneDoeAccount() {
        return new Account("Jane Doe", "46c837e7ed8ced857d55dde60a78412b82713a9d22af62c7f593dea7346e2b1e", 100.0);
    }

}
