package br.com.guilherme.server.repository;

import br.com.guilherme.server.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByAccountNumber(Integer accountNumber);
    Optional<Account> findAccountByClientId(Long id);
}
