package br.com.guilherme.server.repository;

import br.com.guilherme.server.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findAccountByAccountNumber(Integer accountNumber);
}
