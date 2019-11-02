package info.ernestas.revoluttest.service;

import info.ernestas.revoluttest.exception.AccountDoesNotExistException;
import info.ernestas.revoluttest.exception.MoneyCanNotBeTransferedException;
import info.ernestas.revoluttest.model.Account;
import info.ernestas.revoluttest.repository.AccountRepository;
import info.ernestas.revoluttest.util.AccountNumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;

public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    @Inject
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account open(String name) {
        String accountNumber = AccountNumberUtil.generateAccountNumber(name);

        Account account = accountRepository.save(new Account(name, accountNumber, 100.0));

        LOGGER.info("Account {} has been opened", account.getAccountNumber());

        return account;
    }

    public Account get(String accountNumber) {
        Optional<Account> account = accountRepository.get(accountNumber);

        if (account.isEmpty()) {
            throw new AccountDoesNotExistException("Account " + accountNumber + " does not exist");
        }

        return account.get();
    }

    public synchronized void transfer(String accountFrom, String accountTo, double amount) {
        Account firstAccount = get(accountFrom);
        if (firstAccount.getBalance() < amount) {
            throw new MoneyCanNotBeTransferedException("Account " + accountFrom + " does not have enough balance to transfer " + amount);
        }

        Account secondAccount = get(accountTo);

        Account updatedFirstAccount = new Account(firstAccount.getName(), firstAccount.getAccountNumber(), firstAccount.getBalance() - amount);
        Account updatedSecondAccount = new Account(secondAccount.getName(), secondAccount.getAccountNumber(), secondAccount.getBalance() + amount);

        accountRepository.update(updatedFirstAccount.getAccountNumber(), updatedFirstAccount);
        accountRepository.update(updatedSecondAccount.getAccountNumber(), updatedSecondAccount);

        LOGGER.info("{} has been transferred from account {} to account {}", amount, accountFrom, accountTo);
    }

}
