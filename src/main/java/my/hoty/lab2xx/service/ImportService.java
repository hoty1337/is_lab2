package my.hoty.lab2xx.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import my.hoty.lab2xx.entity.OperationStatus;
import my.hoty.lab2xx.entity.Vehicle;
import my.hoty.lab2xx.model.Client;
import my.hoty.lab2xx.model.ImportOperation;
import my.hoty.lab2xx.repository.ImportOperationRepo;
import my.hoty.lab2xx.repository.RoleRepo;
import my.hoty.lab2xx.repository.VehicleRepo;
import my.hoty.lab2xx.util.JsonVehicleParser;
import my.hoty.lab2xx.util.VehicleValidator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ImportService {
    private final VehicleRepo vehicleRepo;
    private final ClientService clientService;
    private final ImportOperationRepo importOperationRepo;
    private final VehicleValidator validator;
    private final JsonVehicleParser jsonVehicleParser;
    private final RoleRepo roleRepo;

    public ImportOperation proccessImport(MultipartFile file,  Client initiator) {
        ImportOperation operation = new ImportOperation();
        operation.setImportedBy(initiator);
        operation.setTimestamp(LocalDateTime.now());
        operation.setFilename(file.getOriginalFilename());
        operation.setStatus(OperationStatus.IN_PROGRESS);
        operation = importOperationRepo.save(operation);

        try {
            List<Vehicle> vehicles = parseFile(file);
            validateVehicles(vehicles);
            for (Vehicle vehicle : vehicles) {
                vehicle.setClient(initiator);
                vehicle.setCreationDate(ZonedDateTime.now());
            }

            vehicles = vehicleRepo.saveAll(vehicles);
            operation.setStatus(OperationStatus.SUCCESS);
            operation.setImportedCount(vehicles.size());
        } catch (Exception e) {
            operation.setStatus(OperationStatus.FAILURE);
            System.out.println("Error: " + e.getMessage());;
        } finally {
            importOperationRepo.save(operation);
        }

        return operation;
    }

    public List<ImportOperation> getImportHistory(Client client) {
        if (client.isAdmin()) {
            return importOperationRepo.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
        } else {
            return importOperationRepo.findByImportedBy(client, Sort.by(Sort.Direction.DESC, "timestamp"));
        }
    }

    private List<Vehicle> parseFile(MultipartFile file) throws IOException {
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        if (Objects.equals(file.getContentType(), "application/json")) {
            return jsonVehicleParser.parseJson(content);
        }
        else {
            throw new IllegalArgumentException("Invalid content type");
        }
    }

    private void validateVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            Errors errors = new BeanPropertyBindingResult(vehicle, "vehicle");
            validator.validate(vehicle, errors);
            if (errors.hasErrors()) {
                throw new ValidationException("Validation failed: " + errors.getAllErrors());
            }
        }
    }

}
