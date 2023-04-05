package org.exercise.pizzeria.repository;

import org.exercise.pizzeria.model.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostumerRepository extends JpaRepository<Costumer,Integer> {

}
