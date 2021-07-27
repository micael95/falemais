package br.com.uaubox.falemais.domain.repository;

import br.com.uaubox.falemais.domain.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, String> {}
