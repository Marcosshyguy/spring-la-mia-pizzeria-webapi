package org.exercise.pizzeria.controller;


import jakarta.validation.Valid;
import org.exercise.pizzeria.model.Ingredient;
import org.exercise.pizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model){
        List<Ingredient> ingredients = ingredientRepository.findAll(Sort.by("name"));
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("emptyIngredient", new Ingredient());
        return "ingredients/index";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("ingredient") Ingredient newIngredient, BindingResult bindingResult, Model model){

        return "redirect:/ingredients";
    }
}
