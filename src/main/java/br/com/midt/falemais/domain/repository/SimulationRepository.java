package br.com.midt.falemais.domain.repository;

import br.com.midt.falemais.domain.model.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimulationRepository extends JpaRepository<Simulation, String> {}
