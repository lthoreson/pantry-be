package net.yorksolutions.pantry.services;

import net.yorksolutions.pantry.models.Account;
import net.yorksolutions.pantry.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account login(String username, String password) {
        return repository.findAccountByUsernameAndPassword(username, password).orElseThrow();
    }

    public Account add(Account requestBody) throws Exception {
        final var taken = repository.findAccountByUsername(requestBody.getUsername()).isPresent();
        if (taken) {
            throw new Exception("username is taken");
        }
        return repository.save(requestBody);
    }

    public Account mod(Account requestBody, String username, String password) throws Exception {
        final var auth = login(username, password);
        if (!requestBody.getId().equals(auth.getId())) {
            throw new Exception("account id is incorrect");
        }
        return repository.save(requestBody);
    }

    public void deleteAccount(Long id, String username, String password) throws Exception {
        final var auth = login(username, password);
        if (!id.equals(auth.getId())) {
            throw new Exception("account id is incorrect");
        }
        repository.delete(auth);
    }
}
