package net.yorksolutions.auth.services;

import net.yorksolutions.auth.dto.Credentials;
import net.yorksolutions.auth.models.Account;
import net.yorksolutions.auth.repositories.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public UUID add(Credentials requestBody) throws Exception {
        final var taken = repository.findAccountByUsername(requestBody.username).isPresent();
        if (taken) {
            throw new Exception("username is taken");
        }
        final var newAccount = repository.save(new Account(requestBody));
        return authService.addToken(newAccount.getId());
    }

    public Account getAccount(UUID token) {
        final var auth = authService.checkToken(token);
        return repository.findById(auth).orElseThrow();
    }

    public Iterable<Account> getAllAccounts() {
        return repository.findAll();
    }

    public void mod(Credentials requestBody, UUID token) throws Exception {
        final var auth = authService.checkToken(token);
        // stop if unauthorized
        if (auth.equals(null)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        final var targetAccount = repository.findById(auth).orElseThrow();
        if (!requestBody.username.equals("")) {
            targetAccount.setUsername(requestBody.username);
        }
        if (!requestBody.password.equals("")) {
            targetAccount.setPassword(requestBody.password);
        }
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
