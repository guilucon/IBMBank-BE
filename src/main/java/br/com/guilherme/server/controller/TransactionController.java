package br.com.guilherme.server.controller;

import br.com.guilherme.server.business.TransactionBusiness;
import br.com.guilherme.server.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {

    @Autowired
    private TransactionBusiness transactionBusiness;

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionDTO createdTransaction = transactionBusiness.createTransaction(transactionDTO);
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionDTO>> listAllTransactionsByAccountNumber(@PathVariable String accountNumber) {
        List<TransactionDTO> transactions = transactionBusiness.listAllTransactionsByAccountNumber(accountNumber);
        return ResponseEntity.ok(transactions);
    }
}