package net.yorksolutions.services;

import net.yorksolutions.models.Item;
import net.yorksolutions.repositories.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public Item add(Item requestBody) throws Exception {
        // make sure item doesn't exist
        final var exists = repository.findItemByName(requestBody.getName()).isPresent();
        if (exists) {
            throw new Exception("item already exists");
        }
        return repository.save(requestBody);
    }

    public Item mod(Item requestBody) {
        // make sure item exists
        repository.findById(requestBody.getId()).orElseThrow();
        return  repository.save(requestBody);
    }

    public Iterable<Item> get() {
        return repository.findAll();
    }
}
