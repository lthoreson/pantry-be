package net.yorksolutions.services;

import net.yorksolutions.models.Account;
import net.yorksolutions.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account authenticate(String username, String password) {
        return repository.findAccountByUsernameAndPassword(username, password).orElseThrow();
    }

    public Account add(Account requestBody) throws Exception {
        final var taken = repository.findAccountByUsername(requestBody.getUsername()).isPresent();
        if (taken) {
            throw new Exception("username is taken");
        }
        return repository.save(requestBody);
    }
}
