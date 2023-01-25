package net.yorksolutions.pantry.services;

import net.yorksolutions.pantry.models.Recipe;
import net.yorksolutions.pantry.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class RecipeService {
    private final RecipeRepository repository;
    private RestTemplate client = new RestTemplate();
    @Value("${net.yorksolutions.authUrl}")
    private String authUrl;

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

    public void delete(Long id, UUID token) throws Exception {
        final var url = String.format("%s/auth?token=%s", authUrl, token);
        final var auth = client.getForObject(url, Long.class);
        final var targetRecipe = repository.findById(id).orElseThrow();
        if (!targetRecipe.getAccount().getId().equals(auth)) {
            throw new Exception("only recipe owner can delete");
        }
        repository.deleteById(id);
    }
}
