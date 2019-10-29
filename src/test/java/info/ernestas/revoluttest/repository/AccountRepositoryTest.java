package info.ernestas.revoluttest.repository;

import info.ernestas.revoluttest.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountRepositoryTest {

    private static final Account FIRST_ACCOUNT = new Account("John Doe", "54b0c38c723c7852334526fd0127bc165ea0b9d9b4034bb6b93195704766b677", 0.0);
    private static final Account SECOND_ACCOUNT = new Account("Jane Doe", "46c837e7ed8ced857d55dde60a78412b82713a9d22af62c7f593dea7346e2b1e", 0.0);

    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
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
        accountRepository.save(FIRST_ACCOUNT);
        accountRepository.save(SECOND_ACCOUNT);

        Optional<Account> secondAccount = accountRepository.get(SECOND_ACCOUNT.getAccountNumber());

        assertTrue(secondAccount.isPresent());
        assertThat(secondAccount.get(), is(SECOND_ACCOUNT));
    }

    @Test
    void shouldNotReturnAccount_whenAccountDoesNotExist() {
        accountRepository.save(FIRST_ACCOUNT);

        Optional<Account> secondAccount = accountRepository.get(SECOND_ACCOUNT.getAccountNumber());

        assertTrue(secondAccount.isEmpty());
    }
}