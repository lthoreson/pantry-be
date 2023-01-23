package net.yorksolutions.pantry.repositories;

import net.yorksolutions.pantry.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    Optional<Item> findItemByName(String name);
    Iterable<Item> findAllByOrderByName();
}
