package org.exercise.pizzeria.controller;

import jakarta.validation.Valid;
import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.repository.PizzaRepository;
import org.exercise.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController{
    @Autowired
    PizzaRepository pizzaRepository;

//    @Autowired
//    PizzaService pizzaService;
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
        return "/pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id , Model model){
//        Pizza p = pizzaRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id" +id + "not found"));

        Optional<Pizza> result = pizzaRepository.findById(id);
        if(result.isPresent()){
            model.addAttribute("pizza", result.get());
            return "/pizzas/show";
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("pizza", new Pizza());
        return "/pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formBookData, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "/pizzas/create";
        }
        pizzaRepository.save(formBookData);
        return "redirect:/pizzas";
    }

}
