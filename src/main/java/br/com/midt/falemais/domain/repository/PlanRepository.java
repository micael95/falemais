package br.com.midt.falemais.domain.repository;

import br.com.midt.falemais.domain.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, String> {
    Optional<Plan> findByPlanIdAndActive(String id, boolean active);
}
