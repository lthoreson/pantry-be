package net.yorksolutions.pantry.services;

import net.yorksolutions.pantry.models.Recipe;
import net.yorksolutions.pantry.repositories.AccountRepository;
import net.yorksolutions.pantry.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    private final RecipeRepository repository;
    private final AccountService accountService;

    public RecipeService(RecipeRepository repository, AccountRepository accountRepository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
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

    public void delete(Long id, String username, String password) throws Exception {
        final var auth = accountService.login(username, password);
        final var targetRecipe = repository.findById(id).orElseThrow();
        if (!targetRecipe.getAccount().equals(auth)) {
            throw new Exception("only recipe owner can delete");
        }
        repository.deleteById(id);
    }
}
