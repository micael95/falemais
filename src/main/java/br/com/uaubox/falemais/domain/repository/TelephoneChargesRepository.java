package br.com.uaubox.falemais.domain.repository;

import br.com.uaubox.falemais.domain.model.Customer;
import br.com.uaubox.falemais.domain.model.TelephoneCharges;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TelephoneChargesRepository extends JpaRepository<TelephoneCharges, String> {
    Optional<TelephoneCharges> findByOriginAndDestination(Integer origin, Integer destination);
}
