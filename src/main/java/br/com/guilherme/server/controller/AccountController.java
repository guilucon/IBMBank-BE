package br.com.guilherme.server.controller;

import br.com.guilherme.server.business.AccountBusiness;
import br.com.guilherme.server.dto.AccountDTO;
import br.com.guilherme.server.dto.AccountDetailsDTO;
import br.com.guilherme.server.dto.AccountListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {
    @Autowired
    private AccountBusiness accountBusiness;

    @GetMapping
    public List<AccountListDTO> getAllClients() {
        return accountBusiness.getAccounts();
    }

    @GetMapping("/client/{id}")
    public AccountDTO getAccountByClientId(@PathVariable Long id) {
        return accountBusiness.getAccountByClientId(id);
    }

    @GetMapping("/{accountNumber}")
    public AccountDTO getAccountByAccountNumberId(@PathVariable Integer accountNumber) {
        return accountBusiness.getAccountByAccountNumber(accountNumber);
    }

    @GetMapping("/details/{accountNumber}")
    public AccountDetailsDTO getAccountDetailsByAccountNumber(@PathVariable Integer accountNumber) {
        return accountBusiness.getAccountDetailsByAccountNumber(accountNumber);
    }
}