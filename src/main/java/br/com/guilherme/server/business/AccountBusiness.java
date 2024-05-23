package br.com.guilherme.server.business;

import br.com.guilherme.server.dto.*;
import br.com.guilherme.server.model.Account;
import br.com.guilherme.server.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountBusiness {
    @Autowired
    private AccountRepository accountRepository;


    public AccountDTO getAccountByAccountNumber(Integer accountNumber) {
        Optional<Account> accountOptional = accountRepository.findAccountByAccountNumber(accountNumber);
        if (accountOptional.isPresent()) {
            return mapToAccountDTO(accountOptional.get());
        } else {
            throw new RuntimeException("Account not found!");
        }
    }

    public AccountDetailsDTO getAccountDetailsByAccountNumber(Integer accountNumber) {
        Optional<Account> accountOptional = accountRepository.findAccountByAccountNumber(accountNumber);
        if (accountOptional.isPresent()) {
            return mapToAccountDetailsDTO(accountOptional.get());
        } else {
            throw new RuntimeException("Account not found!");
        }
    }

    public AccountDTO getAccountByClientId(Long clientId) {
        Optional<Account> accountOptional = accountRepository.findAccountByClientId(clientId);
        if (accountOptional.isPresent()) {
            return mapToAccountDTO(accountOptional.get());
        } else {
            throw new RuntimeException("Account not found!");
        }
    }

    public List<AccountListDTO> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(this::mapToAccountListDTO).collect(Collectors.toList());
    }

    private AccountDTO mapToAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setBalance(account.getBalance());

        if (account.getClient() != null) {
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setId(account.getClient().getId());
            clientDTO.setName(account.getClient().getName());
            clientDTO.setAge(account.getClient().getAge());
            clientDTO.setEmail(account.getClient().getEmail());
            clientDTO.setAccountNumber(account.getClient().getAccountNumber());
            accountDTO.setClient(clientDTO);
        }

        if (account.getTransactions() != null) {
            List<TransactionDTO> transactionDTOs = account.getTransactions().stream()
                    .map(transaction -> {
                        TransactionDTO transactionDTO = new TransactionDTO();
                        transactionDTO.setId(transaction.getId());
                        transactionDTO.setTransactionType(transaction.getTransactionType());
                        transactionDTO.setAmount(transaction.getAmount());
                        transactionDTO.setAccountNumber(transaction.getAccount().getAccountNumber());
                        transactionDTO.setTransactionDate(transaction.getDate());
                        return transactionDTO;
                    }).collect(Collectors.toList());
            accountDTO.setTransactions(transactionDTOs);
        }

        return accountDTO;
    }

    private AccountListDTO mapToAccountListDTO(Account account) {
        AccountListDTO accountListDTO = new AccountListDTO();
        accountListDTO.setId(account.getId());
        accountListDTO.setAccountNumber(account.getAccountNumber());
        accountListDTO.setBalance(account.getBalance());
        accountListDTO.setClientId(account.getClient() != null ? account.getClient().getId() : null);
        accountListDTO.setClientName(account.getClient() != null ? account.getClient().getName() : null);

        return accountListDTO;
    }

    private AccountDetailsDTO mapToAccountDetailsDTO(Account account) {
        AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO();
        accountDetailsDTO.setAccountNumber(account.getAccountNumber());
        accountDetailsDTO.setBalance(account.getBalance());
        accountDetailsDTO.setClientName(account.getClient() != null ? account.getClient().getName() : null);
        accountDetailsDTO.setClientAge(account.getClient() != null ? account.getClient().getAge() : null);
        accountDetailsDTO.setClientEmail(account.getClient() != null ? account.getClient().getEmail() : null);

        return accountDetailsDTO;
    }
}