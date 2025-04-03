package my.hoty.lab2xx.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import my.hoty.lab2xx.entity.OperationStatus;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class ImportOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String filename;
    private OperationStatus status;
    private int importedCount;

    @ManyToOne
    private Client importedBy;
}
