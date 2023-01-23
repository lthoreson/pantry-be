package net.yorksolutions.pantry.services;

import net.yorksolutions.pantry.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class AuthService {
    private HashMap<Long, UUID> tokenMap = new HashMap<>();
    private final AccountRepository accountRepository;

    public AuthService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public UUID addToken(Long id) {
        final var token = UUID.randomUUID();
        tokenMap.put(id, token);
        return token;
    }
}
