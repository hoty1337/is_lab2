package my.hoty.lab2xx.service;

import my.hoty.lab2xx.model.Client;

import java.util.List;

public interface ClientService {
    boolean save(Client client);
    Client findByUsername(String name);
    List<Client> findAll();
    void updateClientRole(String username, String roleName);
}
