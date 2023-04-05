package org.exercise.pizzeria.controller;

import org.exercise.pizzeria.repository.PizzaRepository;
import org.exercise.pizzeria.repository.PremiumDealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(name = "/premium")
public class PremiumDealController {
    @Autowired
    private PremiumDealRepository premiumDealRepository;
    @Autowired
    private PizzaRepository pizzaRepository;

//    @GetMapping("/create")
//    public String create(@RequestParam(name = "pizzaId") Optional<Integer> id, Model model){
//        PremiumDeal costumers = new PremiumDeal();
//
//
//
//        return "/costumers/create";
//    }

}
