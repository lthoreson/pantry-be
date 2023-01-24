package net.yorksolutions.auth.services;

import net.yorksolutions.auth.repositories.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.UUID;

@Service
public class AuthService {
    private HashMap<UUID, Long> tokenMap = new HashMap<>();
    private final AccountRepository accountRepository;

    public AuthService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // adds token/userId pair to hash map
    public UUID addToken(Long id) {
        final var token = UUID.randomUUID();
        tokenMap.put(token, id);
        return token;
    }

    // return user id for token, else null
    public Long checkToken(UUID token) {
        final var accountId = tokenMap.get(token);
        if (accountId.equals(null)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return accountId;
    }
}
