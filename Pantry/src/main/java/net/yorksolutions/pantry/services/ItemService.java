package net.yorksolutions.pantry.services;

import net.yorksolutions.pantry.models.Item;
import net.yorksolutions.pantry.models.Recipe;
import net.yorksolutions.pantry.repositories.ItemRepository;
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

    public void take(Recipe requestBody) throws Exception {
        // check if all ingredients exist and have enough in the pantry
        for (var ingredient : requestBody.getIngredients()) {
            final var item = repository.findById(ingredient.getItem().getId()).orElseThrow();
            if (ingredient.getWeight() > item.getQuantity()) {
                throw new Exception("not enough in the pantry for this recipe");
            }
        }
        // retrieve each ingredient item and reduce its quantity by ingredient weight
        for (var ingredient : requestBody.getIngredients()) {
            final var item = repository.findById(ingredient.getItem().getId()).orElseThrow();
            item.setQuantity(item.getQuantity() - ingredient.getWeight());
            repository.save(item);
        }
        repository.findAllByOrderByName();
    }

    public Iterable<Item> get() {
        return repository.findAllByOrderByName();
    }

    public void deleteItem(Long id) {
        repository.findById(id).orElseThrow();
        repository.deleteById(id);
    }
}
