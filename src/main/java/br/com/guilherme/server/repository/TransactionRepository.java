package br.com.guilherme.server.repository;

import br.com.guilherme.server.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.account.accountNumber = :accountNumber")
    List<Transaction> findByAccountNumber(String accountNumber);
}
