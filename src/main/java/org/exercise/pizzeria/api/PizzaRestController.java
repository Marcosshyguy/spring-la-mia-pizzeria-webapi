package org.exercise.pizzeria.api;

import jakarta.validation.Valid;
import org.exercise.pizzeria.model.Ingredient;
import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.model.PremiumDeal;
import org.exercise.pizzeria.repository.IngredientRepository;
import org.exercise.pizzeria.repository.PizzaRepository;
import org.exercise.pizzeria.repository.PremiumDealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/pizzas")
public class PizzaRestController {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private PremiumDealRepository premiumDealRepository;
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


    @PostMapping
    public Pizza create(@Valid @RequestBody Pizza pizza){
        Pizza pizzaToCreate = new Pizza();
        pizzaToCreate.setName(pizza.getName());
        pizzaToCreate.setDescription(pizza.getDescription());
        pizzaToCreate.setImage(pizza.getImage());
        pizzaToCreate.setPrice(pizza.getPrice());
        pizzaToCreate.setIngredientSet(pizza.getIngredientSet());
        return pizzaRepository.save(pizzaToCreate);
    }


    @PutMapping("/{id}")
    public Pizza update(@PathVariable("id") Integer id, @Valid @RequestBody Pizza pizza){
        Pizza pizzaToUpdate = pizzaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pizza Id:" + id));

        Set<Ingredient> ingredientsToUpdate = new HashSet<>();
        List<PremiumDeal> dealsToUpdate = new ArrayList<>();


        pizzaToUpdate.setName(pizza.getName());
        pizzaToUpdate.setDescription(pizza.getDescription());
        pizzaToUpdate.setImage(pizza.getImage());
        pizzaToUpdate.setPrice(pizza.getPrice());

        for( Ingredient ingredient : pizza.getIngredientSet()){
            ingredientsToUpdate.add(ingredientRepository.findById(ingredient.getId()).orElseThrow());
        }
        pizzaToUpdate.setIngredientSet(ingredientsToUpdate);
        pizzaRepository.save(pizzaToUpdate);
//        essendo PremiumDeal l'owner della relazione bisogna inserirla una pizza nel premium deal e non in il contrario
//        quindi dopo aver creato una istanza salvato nella repository la pizza che ci arriva dal form o da un body Api
//        cicliamo tutte le pizze che ci arrivano dalla request e dentro inseriamo l'istnanza della pizza  creata appena prima
        for (PremiumDeal deal : pizza.getPremiumDeals()){
            PremiumDeal premium = premiumDealRepository.findById(deal.getId()).orElseThrow();
            premium.setPizza(pizzaToUpdate);
            premiumDealRepository.save(premium);
        }

        return pizzaToUpdate;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        pizzaRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        pizzaRepository.deleteById(id);
    }
}
