package br.com.uaubox.falemais.domain.repository;

import br.com.uaubox.falemais.domain.model.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimulationRepository extends JpaRepository<Simulation, String> {}
