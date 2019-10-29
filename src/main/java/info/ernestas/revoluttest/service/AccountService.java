package info.ernestas.revoluttest.service;

import info.ernestas.revoluttest.exception.AccountDoesNotExistException;
import info.ernestas.revoluttest.exception.CanNotOpenAccountException;
import info.ernestas.revoluttest.model.Account;
import info.ernestas.revoluttest.model.AccountOpenInfo;
import info.ernestas.revoluttest.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    @Inject
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account open(AccountOpenInfo accountOpenInfo) {
        String accountNumber = generateAccountNumber(accountOpenInfo);

        Account account = accountRepository.save(new Account(accountOpenInfo.getName(), accountNumber, 0.0));

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

    private String generateAccountNumber(AccountOpenInfo accountOpenInfo) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String message = accountOpenInfo.getName() + LocalDateTime.now();
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));

            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new CanNotOpenAccountException("Can not generate account number");
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }
        return hexString.toString();
    }

}
