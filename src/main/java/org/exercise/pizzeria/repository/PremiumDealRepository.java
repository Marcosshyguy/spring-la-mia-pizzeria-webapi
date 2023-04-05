package org.exercise.pizzeria.repository;

import org.exercise.pizzeria.model.PremiumDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumDealRepository extends JpaRepository<PremiumDeal,Integer> {

}
