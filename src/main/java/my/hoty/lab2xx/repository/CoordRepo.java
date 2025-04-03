package my.hoty.lab2xx.repository;

import my.hoty.lab2xx.entity.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordRepo extends JpaRepository<Coordinates, Integer> {
    Coordinates findById(Long id);
}
