package org.exercise.pizzeria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pizzaId;
    @NotBlank(message = "Campo vuoto")
    @Size(min = 5, max = 20, message = "Il nome deve essere maggiore di 5 carattteri e minore di 20")
    private String name;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Campo vuoto")
    private String description;
    @NotNull(message = "Campo vuoto")
    private double price;
    @NotBlank(message = "Campo vuoto")
    @Column(columnDefinition = "TEXT")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
