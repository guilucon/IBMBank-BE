package br.com.guilherme.server.controller;

import br.com.guilherme.server.business.ClientBusiness;
import br.com.guilherme.server.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {
    @Autowired
    private ClientBusiness clientBusiness;


    @PostMapping
    public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
        return clientBusiness.createClient(clientDTO);
    }

    @GetMapping
    public List<ClientDTO> getAllClients() {
        return clientBusiness.listAllClients();
    }

    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {
        return clientBusiness.getClientById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientBusiness.deleteClient(id);
    }
}