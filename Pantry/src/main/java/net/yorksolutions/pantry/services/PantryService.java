package net.yorksolutions.pantry.services;

import net.yorksolutions.pantry.models.Account;
import net.yorksolutions.pantry.models.Pantry;
import net.yorksolutions.pantry.repositories.PantryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PantryService {
    private final PantryRepository repository;
    private RestTemplate client = new RestTemplate();
    @Value("${net.yorksolutions.authUrl}")
    private String authUrl;

    public PantryService(PantryRepository repository) {
        this.repository = repository;
    }

    public Iterable<Pantry> getAll(UUID token) {
        final var url = String.format("%s/auth?token=%s", authUrl, token);
        final var auth = client.getForObject(url, Long.class);
        final var memberships = repository.findAllByMembersId(auth);
        final var ownerships = repository.findAllByOwnerId(auth);
        return Stream.concat(memberships.stream(), ownerships.stream()).toList();
    }

    public Pantry getPantry(Long id, UUID token) {
        final var url = String.format("%s/auth?token=%s", authUrl, token);
        final var auth = client.getForObject(url, Long.class);
        final var target = repository.findById(id).orElseThrow();
        final var memberIds = target.getMembers().stream().map(Account::getId).collect(Collectors.toList());
        // viewing pantry unauthorized if you are not a member or owner
        if (!memberIds.contains(auth) && !target.getOwner().getId().equals(auth)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return target;
    }

    public Pantry addPantry(Pantry requestBody, UUID token) {
        final var url = String.format("%s/auth?token=%s", authUrl, token);
        final var auth = client.getForObject(url, Long.class);
        if (!auth.equals(requestBody.getOwner().getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        final var newPantry = new Pantry();
        newPantry.setName(requestBody.getName());
        newPantry.setOwner(requestBody.getOwner());
        newPantry.setMembers(requestBody.getMembers());
        newPantry.setItems(requestBody.getItems());
        return repository.save(newPantry);
    }

    public Pantry modPantry(Pantry requestBody, UUID token) {
        final var url = String.format("%s/auth?token=%s", authUrl, token);
        final var auth = client.getForObject(url, Long.class);
        final var oldPantry = repository.findById(requestBody.getId()).orElseThrow();
        final var memberIds = oldPantry.getMembers().stream().map(Account::getId).collect(Collectors.toList());
        final var oldPantryOwnerId = oldPantry.getOwner().getId();
        final var newPantryOwnerId = requestBody.getOwner().getId();
        // edit pantry unauthorized if you are not a member or owner
        if (!memberIds.contains(auth) && !auth.equals(oldPantryOwnerId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        // change owner unauthorized if you are not the current owner
        if (!auth.equals(oldPantryOwnerId) && !oldPantryOwnerId.equals(newPantryOwnerId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return repository.save(requestBody);
    }

    public void deletePantry(Long id, UUID token) {
        final var url = String.format("%s/auth?token=%s", authUrl, token);
        final var auth = client.getForObject(url, Long.class);
        final var target = repository.findById(id).orElseThrow();
        final var targetOwnerId = target.getOwner().getId();
        // delete pantry unauthorized if you are not the owner
        if (!auth.equals(targetOwnerId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        repository.deleteById(id);
    }
}
