package net.yorksolutions.pantry.controllers;

import net.yorksolutions.pantry.models.Account;
import net.yorksolutions.pantry.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping(params = {"username", "password"})
    public UUID login(@RequestParam String username, @RequestParam String password) {
        try {
            return service.login(username, password);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping(params = {"token"})
    public Account getAccount(@RequestParam UUID token) {
        try {
            return service.getAccount(token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping
    public UUID add(@RequestBody Account requestBody) {
        try {
            return service.add(requestBody);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public void mod(@RequestBody Account requestBody, @RequestParam UUID token) {
        try {
            service.mod(requestBody, token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void mod(@PathVariable Long id, @RequestParam UUID token) {
        try {
            service.deleteAccount(id, token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
