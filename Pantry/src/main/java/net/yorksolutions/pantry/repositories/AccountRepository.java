package net.yorksolutions.pantry.repositories;

import net.yorksolutions.pantry.models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findAccountByUsernameAndPassword(String username, String password);
    Optional<Account> findAccountByUsername(String username);
}
