package net.yorksolutions.pantry.repositories;

import net.yorksolutions.pantry.models.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Iterable<Recipe> findRecipesByAccount_Id(Long accountId);
}
