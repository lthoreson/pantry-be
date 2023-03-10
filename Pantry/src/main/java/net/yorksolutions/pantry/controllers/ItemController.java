package net.yorksolutions.pantry.controllers;

import net.yorksolutions.pantry.models.Item;
import net.yorksolutions.pantry.models.Recipe;
import net.yorksolutions.pantry.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/item")
public class ItemController {
    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Item> get() {
        try {
            return service.get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Item add(@RequestBody Item requestBody) {
        try {
            return service.add(requestBody);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public Item mod(@RequestBody Item requestBody) {
        try {
            return service.mod(requestBody);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/take")
    public void mod(@RequestBody Recipe requestBody) {
        try {
            service.take(requestBody);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        try {
            service.deleteItem(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
