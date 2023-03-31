package org.exercise.pizzeria.service;

import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {
    @Autowired
    PizzaRepository pizzaRepository;

    public Pizza createPizza(Pizza formBook){
        return null;
    }
}
