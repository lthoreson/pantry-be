package net.yorksolutions.repositories;

import net.yorksolutions.models.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Iterable<Recipe> findRecipesByAccount_Id(Long accountId);
}
