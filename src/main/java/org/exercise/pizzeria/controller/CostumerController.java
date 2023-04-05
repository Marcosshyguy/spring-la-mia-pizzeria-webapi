package org.exercise.pizzeria.controller;

import org.exercise.pizzeria.model.Costumer;
import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.repository.CostumerRepository;
import org.exercise.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping(name = "/costumer")
public class CostumerController {
    @Autowired
    private CostumerRepository costumerRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId") Optional<Integer> id, Model model){
        Costumer costumers = new Costumer();
//        If(id.isPresent()){
//
//            Optional<Pizza> pizza = pizzaRepository.findById(id.get());
//        }


        return "/costumers/create";
    }

}
