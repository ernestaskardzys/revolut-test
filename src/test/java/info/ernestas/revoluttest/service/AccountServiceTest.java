package info.ernestas.revoluttest.service;

import info.ernestas.revoluttest.data.AccountTestData;
import info.ernestas.revoluttest.exception.AccountDoesNotExistException;
import info.ernestas.revoluttest.exception.MoneyCanNotBeTransferedException;
import info.ernestas.revoluttest.model.Account;
import info.ernestas.revoluttest.model.AccountOpenInfo;
import info.ernestas.revoluttest.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceTest {

    private static final String JOHN_DOE = "John Doe";

    private Account firstAccount;
    private Account secondAccount;

    private AccountRepository accountRepository;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();

        accountService = new AccountService(accountRepository);

        AccountOpenInfo johnJuniorAccount = new AccountOpenInfo("John Junior");
        AccountOpenInfo jackBlackAccount = new AccountOpenInfo("Jack Black");

        firstAccount = accountService.open(johnJuniorAccount);
        secondAccount = accountService.open(jackBlackAccount);
    }

    @Test
    void open() {
        AccountOpenInfo info = new AccountOpenInfo(JOHN_DOE);

        Account account = accountService.open(info);

        assertThat(account.getName(), is(JOHN_DOE));
        assertThat(account.getAccountNumber(), is(notNullValue()));
        assertThat(account.getBalance(), is(100.0));
    }

    @Test
    void get() {
        Account johnDoeAccount = AccountTestData.getJohnDoeAccount();

        accountRepository.save(johnDoeAccount);

        Account result = accountService.get(johnDoeAccount.getAccountNumber());

        assertThat(result, is(johnDoeAccount));
    }

    @Test
    void shouldNotGetAccount_whenAccountIsNotFound() {
        assertThrows(AccountDoesNotExistException.class, () -> accountService.get("not_existing_account_number"));
    }

    @Test
    void transfer() {
        final double amountToTransfer = 51.01;

        accountService.transfer(firstAccount.getAccountNumber(), secondAccount.getAccountNumber(), amountToTransfer);

        Account johnAccount = accountRepository.get(firstAccount.getAccountNumber()).get();
        Account jackAccount = accountRepository.get(secondAccount.getAccountNumber()).get();

        assertThat(johnAccount.getBalance(), is(firstAccount.getBalance() - amountToTransfer));
        assertThat(jackAccount.getBalance(), is(firstAccount.getBalance() + amountToTransfer));
    }

    @Test
    void mustNotTransferMoney_ifFirstAccountDoesNotHaveEnoughMoney() {
        assertThrows(MoneyCanNotBeTransferedException.class, () -> accountService.transfer(firstAccount.getAccountNumber(), secondAccount.getAccountNumber(), 1000));
    }
}