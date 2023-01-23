package net.yorksolutions.pantry.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToOne;

@Embeddable
public class Ingredient {
    @OneToOne
    private Item item;
    private Double weight;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
