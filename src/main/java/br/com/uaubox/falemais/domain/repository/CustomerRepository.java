package br.com.uaubox.falemais.domain.repository;

import br.com.uaubox.falemais.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
