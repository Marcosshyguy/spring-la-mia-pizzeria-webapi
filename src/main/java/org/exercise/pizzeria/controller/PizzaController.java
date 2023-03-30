package org.exercise.pizzeria.controller;

import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController{
    @Autowired
    PizzaRepository pizzaRepository;
    @GetMapping
    public String index(Model model, @RequestParam(name = "type") Optional<String> keyword){

        List<Pizza> pizzas;

        if (keyword.isEmpty()) {
            pizzas = pizzaRepository.findAll(Sort.by("name"));
        } else {
            pizzas = pizzaRepository.findByNameContainingIgnoreCase(keyword.get());
            model.addAttribute("keyword", keyword.get());
        }
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id , Model model){
//        Pizza p = pizzaRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id" +id + "not found"));

        Optional<Pizza> result = pizzaRepository.findById(id);
        if(result.isPresent()){
            model.addAttribute("pizza", result.get());
            return "pizzas/show";
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
    }

}
