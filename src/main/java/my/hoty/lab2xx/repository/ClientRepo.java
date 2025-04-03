package my.hoty.lab2xx.repository;

import my.hoty.lab2xx.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {

    Client findByUsername(String username);
}
