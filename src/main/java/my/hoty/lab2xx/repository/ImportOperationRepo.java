package my.hoty.lab2xx.repository;

import my.hoty.lab2xx.model.Client;
import my.hoty.lab2xx.model.ImportOperation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportOperationRepo extends JpaRepository<ImportOperation, Integer> {
    List<ImportOperation> findByImportedBy(Client importedBy, Sort sort);
}
