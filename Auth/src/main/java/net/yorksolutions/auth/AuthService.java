package net.yorksolutions.auth;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class AuthService {
    private HashMap<Long, UUID> tokenMap = new HashMap<>();


}
