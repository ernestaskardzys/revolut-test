package info.ernestas.revoluttest.repository;

import info.ernestas.revoluttest.data.AccountTestData;
import info.ernestas.revoluttest.exception.AccountCanNotBeUpdatedException;
import info.ernestas.revoluttest.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountRepositoryTest {

    private static final Account FIRST_ACCOUNT = AccountTestData.getJohnDoeAccount();
    private static final Account SECOND_ACCOUNT = AccountTestData.getJaneDoeAccount();

    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();

        accountRepository.save(FIRST_ACCOUNT);
    }

    @Test
    void save() {
        Account resultingAccount = accountRepository.save(FIRST_ACCOUNT);

        assertThat(resultingAccount.getName(), is(FIRST_ACCOUNT.getName()));
        assertThat(resultingAccount.getAccountNumber(), is(FIRST_ACCOUNT.getAccountNumber()));
        assertThat(resultingAccount.getBalance(), is(FIRST_ACCOUNT.getBalance()));
    }

    @Test
    void get() {
        accountRepository.save(SECOND_ACCOUNT);

        Optional<Account> secondAccount = accountRepository.get(SECOND_ACCOUNT.getAccountNumber());

        assertTrue(secondAccount.isPresent());
        assertThat(secondAccount.get(), is(SECOND_ACCOUNT));
    }

    @Test
    void shouldNotReturnAccount_whenAccountDoesNotExist() {
        Optional<Account> secondAccount = accountRepository.get(SECOND_ACCOUNT.getAccountNumber());

        assertTrue(secondAccount.isEmpty());
    }

    @Test
    void update() {
        Account johnSmith = new Account("John Smith", "account_number", 100.0);
        accountRepository.save(johnSmith);

        Account updatedAccount = new Account(johnSmith.getName(), johnSmith.getAccountNumber(), johnSmith.getBalance() + 10);

        accountRepository.update(johnSmith.getAccountNumber(), updatedAccount);

        Account resultingAccount = accountRepository.get(updatedAccount.getAccountNumber()).get();

        assertThat(resultingAccount.getName(), is(updatedAccount.getName()));
        assertThat(resultingAccount.getAccountNumber(), is(updatedAccount.getAccountNumber()));
        assertThat(resultingAccount.getBalance(), is(updatedAccount.getBalance()));
    }

    @Test
    void accountWillNotBeUpdated_ifItDoesNotExist() {
        String notExistingAccountNumber = "not_existing_account_number";

        assertThrows(AccountCanNotBeUpdatedException.class, () -> accountRepository.update(notExistingAccountNumber, null));
    }
}