package org.exercise.pizzeria.controller;

import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pizza")
public class PizzaController{
    @Autowired
    PizzaRepository pizzaRepository;
    @GetMapping
    public String index(Model model){
        List<Pizza> pizzas = pizzaRepository.findAll();
        model.addAttribute("pizzas",pizzas);
        return "pizzas";
    }

}
