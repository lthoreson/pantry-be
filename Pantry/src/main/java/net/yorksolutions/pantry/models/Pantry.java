package net.yorksolutions.pantry.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Pantry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ManyToOne
    private Account owner;
    @ManyToMany
    private List<Account> members;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public List<Account> getMembers() {
        return members;
    }

    public void setMembers(List<Account> members) {
        this.members = members;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
