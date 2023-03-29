package org.exercise.pizzeria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pizza_id;

    private String name;

    private String description;

    private Double price;

    public int getPizza_id() {
        return pizza_id;
    }

    public void setPizza_id(int pizza_id) {
        this.pizza_id = pizza_id;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
