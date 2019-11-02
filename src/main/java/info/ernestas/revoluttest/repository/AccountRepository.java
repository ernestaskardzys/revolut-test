package info.ernestas.revoluttest.repository;

import info.ernestas.revoluttest.exception.AccountCanNotBeUpdatedException;
import info.ernestas.revoluttest.model.Account;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {

    private static final ConcurrentHashMap<String, Account> REPOSITORY = new ConcurrentHashMap<>();

    public Optional<Account> get(String accountNumber) {
        if (!REPOSITORY.containsKey(accountNumber)) {
            return Optional.empty();
        }

        return Optional.of(REPOSITORY.get(accountNumber));
    }

    public Account save(Account account) {
        REPOSITORY.put(account.getAccountNumber(), account);

        return account;
    }

    public void update(String accountNumber, Account updatedAccount) {
        Account account = REPOSITORY.get(accountNumber);

        if (account != null) {
            REPOSITORY.put(accountNumber, updatedAccount);
        } else {
            throw new AccountCanNotBeUpdatedException("Account " + accountNumber + " can not be updated!");
        }
    }

}
