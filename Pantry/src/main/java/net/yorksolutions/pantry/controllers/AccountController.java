package net.yorksolutions.pantry.controllers;

import net.yorksolutions.pantry.models.Account;
import net.yorksolutions.pantry.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping(params = {"username", "password"})
    public Account login(@RequestParam String username, @RequestParam String password) {
        try {
            return service.login(username, password);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping
    public Account add(@RequestBody Account requestBody) {
        try {
            return service.add(requestBody);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public Account mod(@RequestBody Account requestBody, @RequestParam String username, @RequestParam String password) {
        try {
            return service.mod(requestBody, username, password);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void mod(@PathVariable Long id, @RequestParam String username, @RequestParam String password) {
        try {
            service.deleteAccount(id, username, password);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
