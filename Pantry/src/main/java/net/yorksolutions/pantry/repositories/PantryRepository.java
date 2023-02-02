package net.yorksolutions.pantry.repositories;

import net.yorksolutions.pantry.models.Pantry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PantryRepository extends CrudRepository<Pantry, Long> {
    ArrayList<Pantry> findAllByMembersId(Long id);
    ArrayList<Pantry> findAllByOwnerId(Long id);
    }
