package br.com.guilherme.server.business;

import br.com.guilherme.server.dto.TransactionDTO;
import br.com.guilherme.server.model.Account;
import br.com.guilherme.server.model.Transaction;
import br.com.guilherme.server.model.TransactionType;
import br.com.guilherme.server.repository.AccountRepository;
import br.com.guilherme.server.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionBusiness {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDate(LocalDate.now());

        Account account = accountRepository.findAccountByAccountNumber(transactionDTO.getAccountNumber())
                                            .orElseThrow(() -> new RuntimeException("Account not found!"));
        transaction.setAccount(account);

        if (transactionDTO.getTransactionType() == TransactionType.CREDITO) {
            account.setBalance(account.getBalance() + transactionDTO.getAmount());
        } else if (transactionDTO.getTransactionType() == TransactionType.DEBITO) {
            account.setBalance(account.getBalance() - transactionDTO.getAmount());
        }

        accountRepository.save(account);
        Transaction savedTransaction = transactionRepository.save(transaction);

        transactionDTO.setId(savedTransaction.getId());
        return transactionDTO;
    }

    public List<TransactionDTO> listAllTransactionsByAccountNumber(String accountNumber) {
        List<Transaction> transactions = transactionRepository.findByAccountNumber(accountNumber);
        return transactions.stream().map(this::mapToTransactionDTO).collect(Collectors.toList());
    }

    private TransactionDTO mapToTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setAccountNumber(transaction.getAccount().getAccountNumber());
        transactionDTO.setTransactionDate(transaction.getDate());
        return transactionDTO;
    }
}