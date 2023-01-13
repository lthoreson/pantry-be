package net.yorksolutions.services;

import net.yorksolutions.models.Recipe;
import net.yorksolutions.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public Recipe add(Recipe requestBody) {
        return repository.save(requestBody);
    }

    public Iterable<Recipe> getByAccountId(Long accountId) {
        return repository.findRecipesByAccount_Id(accountId);
    }

    public Recipe mod(Recipe requestBody) {
        repository.findById(requestBody.getId()).orElseThrow();
        return repository.save(requestBody);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
