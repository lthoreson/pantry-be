package net.yorksolutions.pantry.controllers;

import net.yorksolutions.pantry.models.Pantry;
import net.yorksolutions.pantry.services.PantryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/pantry")
public class PantryController {
    private final PantryService service;

    public PantryController(PantryService service) {
        this.service = service;
    }

    @PostMapping
    public Pantry addPantry(@RequestBody Pantry requestBody, @RequestParam UUID token) {
        try {
            return service.addPantry(requestBody, token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping
    public Iterable<Pantry> getAll(@RequestParam UUID token) {
        try {
            return service.getAll(token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public Pantry getPantry(@PathVariable Long id, @RequestParam UUID token) {
        try {
            return service.getPantry(id, token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping
    public Pantry modPantry(@RequestBody Pantry requestBody, @RequestParam UUID token) {
        try {
            return service.modPantry(requestBody, token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deletePantry(@PathVariable Long id, @RequestParam UUID token) {
        try {
            service.deletePantry(id, token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
