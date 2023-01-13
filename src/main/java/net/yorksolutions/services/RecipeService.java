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
        System.out.println(requestBody);
        return repository.save(requestBody);
    }

    public Iterable<Recipe> getByAccountId(Long accountId) {
        return repository.findRecipesByAccount_Id(accountId);
    }
}
