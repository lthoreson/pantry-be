package net.yorksolutions.auth.services;

import net.yorksolutions.auth.models.Account;
import net.yorksolutions.auth.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final AuthService authService;

    public AccountService(AccountRepository repository, AuthService authService) {
        this.repository = repository;
        this.authService = authService;
    }

    public UUID login(String username, String password) {
        final var foundAccount = repository.findAccountByUsernameAndPassword(username, password).orElseThrow();
        return authService.addToken(foundAccount.getId());
    }

    public UUID add(Account requestBody) throws Exception {
        final var taken = repository.findAccountByUsername(requestBody.getUsername()).isPresent();
        if (taken) {
            throw new Exception("username is taken");
        }
        final var newAccount = repository.save(requestBody);
        return authService.addToken(newAccount.getId());
    }

    public Account getAccount(UUID token) {
        final var auth = authService.checkToken(token);
        return repository.findById(auth).orElseThrow();
    }

    public void mod(Account requestBody, UUID token) throws Exception {
        final var auth = authService.checkToken(token);
        final var targetAccount = repository.findById(auth).orElseThrow();
        targetAccount.setUsername(requestBody.getUsername());
        targetAccount.setPassword(requestBody.getPassword());
        repository.save(targetAccount);
    }

    public void deleteAccount(Long id, UUID token) throws Exception {
        final var auth = authService.checkToken(token);
        if (!id.equals(auth)) {
            throw new Exception("account id is incorrect");
        }
        repository.deleteById(auth);
    }
}
