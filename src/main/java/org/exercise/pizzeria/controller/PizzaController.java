package org.exercise.pizzeria.controller;

import jakarta.validation.Valid;
import org.exercise.pizzeria.model.Ingredient;
import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.repository.IngredientRepository;
import org.exercise.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController{
    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    IngredientRepository ingredientRepository;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + id + " non trovata");
        }
    }

    @GetMapping("/create")
    public String create(Model model){
        List<Ingredient> ingredienteList = ingredientRepository.findAll();
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredienteList", ingredienteList);
        return "/pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizzaData, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "/pizzas/create";
        }
        pizzaRepository.save(formPizzaData);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Pizza result = pizzaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id della pizza non valido:" + id));
        model.addAttribute("pizza", result);
        return "pizzas/edit";
    }

    @PutMapping("/edit/{id}")
    public String update( @PathVariable("id") int id, @Valid @ModelAttribute("pizza") Pizza formPizzaData, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "pizzas/edit";
        }
            Pizza pizzaToUpdate = pizzaRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid pizza Id:" + id));

            pizzaToUpdate.setName(formPizzaData.getName());
            pizzaToUpdate.setDescription(formPizzaData.getDescription());
            pizzaToUpdate.setPrice(formPizzaData.getPrice());
            pizzaRepository.save(pizzaToUpdate);

        return "redirect:/pizzas";
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id){
        pizzaRepository.deleteById(id);
        return "redirect:/pizzas";
    }

}
