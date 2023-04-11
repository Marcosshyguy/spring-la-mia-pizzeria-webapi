package org.exercise.pizzeria.api;

import jakarta.validation.Valid;
import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/pizzas")
public class PizzaRestController {
    @Autowired
    private PizzaRepository pizzaRepository;
    @GetMapping
    public List<Pizza> getPizzas(@RequestParam(name = "type") Optional<String> keyword){
        List<Pizza> pizzas;
        if (keyword.isEmpty()) {
            return pizzas = pizzaRepository.findAll();
        } else {
            return pizzas = pizzaRepository.findByNameContainingIgnoreCase(keyword.get());
        }
    }

    @GetMapping("/{id}")
    public Pizza getDetails(@PathVariable("id") Integer id){
        Optional<Pizza> result = pizzaRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + id + " non trovata");
        }
    }

//
//    @PostMapping
//    public Pizza create(@Valid @RequestBody Pizza pizza){
//        Pizza pizzaToCreate = new Pizza();
//        pizzaToCreate.setName(pizza.getName());
//        pizzaToCreate.setDescription(pizza.getDescription());
//        pizzaToCreate.setPrice(pizza.getPrice());
//        pizzaToCreate.setIngredientSet(pizza.getIngredientSet());
//        return pizzaRepository.save(pizzaToCreate);
//    }
//
//    @PutMapping("/{id}")
//    public Pizza update(@PathVariable("id") Integer id, @Valid @RequestBody Pizza pizza){
//        Pizza pizzaToUpdate = pizzaRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid pizza Id:" + id));
//
//        pizzaToUpdate.setName(pizza.getName());
//        pizzaToUpdate.setDescription(pizza.getDescription());
//        pizzaToUpdate.setPrice(pizza.getPrice());
//        pizzaToUpdate.setIngredientSet(pizza.getIngredientSet());
//        return pizzaRepository.save(pizzaToUpdate);
//    }
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable("id") Integer id){
//        pizzaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid pizza Id: " + id));
//
//    }
}
