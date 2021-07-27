package br.com.uaubox.falemais.domain.repository;

import br.com.uaubox.falemais.domain.model.Plan;
import br.com.uaubox.falemais.domain.model.TelephoneCharges;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, String> {
    Optional<Plan> findByPlanIdAndActive(String id, boolean active);
}
