package org.exercise.pizzeria.controller;

import jakarta.validation.Valid;
import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.model.PremiumDeal;
import org.exercise.pizzeria.repository.PizzaRepository;
import org.exercise.pizzeria.repository.PremiumDealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/premium")
public class PremiumDealController {
    @Autowired
    private PremiumDealRepository premiumDealRepository;
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId") Optional<Integer> id, Model model){
        PremiumDeal premiumDeal = new PremiumDeal();
        if(id.isPresent()){
            Optional<Pizza> result  = pizzaRepository.findById(id.get());
            result.ifPresent(premiumDeal::setPizza);
        }
        model.addAttribute("deal",premiumDeal);
        return "premium-deals/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("deal") PremiumDeal dealFormData, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "/premium/create";
        }
        premiumDealRepository.save(dealFormData);
        return "redirect:/pizzas/" + dealFormData.getPizza().getPizzaId();
    }

    @GetMapping("/edit/{id}")
    public String edit (@PathVariable("id") int id, Model model){
        PremiumDeal result = premiumDealRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid deal Id:" + id));
        model.addAttribute("deal", result);
        return "premium-deals/edit";
    }
    @PutMapping("/edit/{id}")
    public String update(@PathVariable("id") int id, @Valid @ModelAttribute("deal") PremiumDeal dealFormUpdatedData,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "premium/edit";
        }
        PremiumDeal dealToUpdate = premiumDealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deal Id:" + id));

        dealToUpdate.setExpiringDate(dealFormUpdatedData.getExpiringDate());
        dealToUpdate.setStartingDate(dealFormUpdatedData.getStartingDate());
        dealToUpdate.setTitle(dealFormUpdatedData.getTitle());

        premiumDealRepository.save(dealToUpdate);

        return "redirect:/pizzas/" + dealToUpdate.getPizza().getPizzaId();
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id){
        premiumDealRepository.deleteById(id);
        return "redirect:/pizzas";

    }
}
