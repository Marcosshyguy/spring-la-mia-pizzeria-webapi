package org.exercise.pizzeria.controller;


import jakarta.validation.Valid;
import org.exercise.pizzeria.model.Ingredient;
import org.exercise.pizzeria.repository.IngredientRepository;
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
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model, @RequestParam(name = "id") Optional<Integer> id){
        List<Ingredient> ingredients = ingredientRepository.findAll(Sort.by("name"));
        model.addAttribute("ingredients", ingredients);
        if(id.isEmpty()){
            model.addAttribute("emptyIngredient", new Ingredient());
        }else{
            Ingredient ingredient = ingredientRepository.findById(id.get()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
            model.addAttribute("ingredientToUpdate", ingredient);
        }

        return "ingredients/index";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("emptyIngredient") Ingredient newIngredient, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "ingredients/index";
        }

        ingredientRepository.save(newIngredient);

        return "redirect:/ingredients";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Ingredient ingredientToUpdate){
        ingredientRepository.save(ingredientToUpdate);
        return "redirect:/ingredients";
    }
}
