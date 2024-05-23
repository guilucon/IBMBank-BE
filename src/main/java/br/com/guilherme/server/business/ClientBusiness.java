package br.com.guilherme.server.business;

import br.com.guilherme.server.dto.ClientDTO;
import br.com.guilherme.server.model.Account;
import br.com.guilherme.server.model.Client;
import br.com.guilherme.server.repository.AccountRepository;
import br.com.guilherme.server.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientBusiness {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setAge(clientDTO.getAge());

        Account account = new Account();
        account.setBalance(0.0);
        accountRepository.save(account);

        account.setAccountNumber((int) (account.getId() + 99));
        client.setAccountNumber(account.getAccountNumber());
        clientRepository.save(client);

        account.setClient(client);
        accountRepository.save(account);

        clientDTO.setId(client.getId());
        clientDTO.setAccountNumber(client.getAccountNumber());

        return clientDTO;
    }

    public List<ClientDTO> listAllClients() {
        return clientRepository.findAll().stream().map(this::mapToClientDTO).collect(Collectors.toList());
    }

    private ClientDTO mapToClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setAge(client.getAge());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setAccountNumber(client.getAccountNumber());
        return clientDTO;
    }

    @Transactional
    public void deleteClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client != null) {
            Optional<Account> account = accountRepository.findAccountByAccountNumber(client.getAccountNumber());
            if (account.isPresent()) {
                accountRepository.delete(account.get());
            }
            clientRepository.delete(client);
        }
    }

    public ClientDTO getClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return mapToClientDTO(client.get());
        } else
            return null;
    }

    public ClientDTO getClientByAccountNumber(Integer accountNumber) {
        Optional<Client> client = clientRepository.findAccountByAccountNumber(accountNumber);
        if (client.isPresent()) {
            return mapToClientDTO(client.get());
        } else {
            throw new RuntimeException("Client not found!");
        }
    }
}