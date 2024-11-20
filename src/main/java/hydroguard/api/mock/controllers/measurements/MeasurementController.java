package hydroguard.api.mock.controllers.measurements;

import hydroguard.api.mock.models.measurements.MeasurementDTO;
import hydroguard.api.mock.models.measurements.MeasurementsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/measurements")
public class MeasurementController {

    @GetMapping("/{sensorId}")
    public MeasurementsDTO getLatestMeasurements(
            @PathVariable UUID sensorId,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1") int pageSize) {

        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }
        if (pageSize < 1 || pageSize > 100) {
            throw new IllegalArgumentException("Page size must be between 1 and 100");
        }

        // Simulate sensor existence check
        boolean sensorExists = checkSensorExists(sensorId);
        if (!sensorExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found");
        }

        List<MeasurementDTO> measurements = new ArrayList<>();
        measurements.add(new MeasurementDTO(sensorId));
        measurements.add(new MeasurementDTO(sensorId));
        measurements.add(new MeasurementDTO(sensorId));
        measurements.add(new MeasurementDTO(sensorId));
        measurements.add(new MeasurementDTO(sensorId));

        if (from != null) {
            measurements = measurements.stream()
                    .filter(measurement -> !measurement.getTimestamp().isBefore(from))
                    .collect(Collectors.toList());
        }
        if (to != null) {
            measurements = measurements.stream()
                    .filter(measurement -> !measurement.getTimestamp().isAfter(to))
                    .collect(Collectors.toList());
        }

        int total = measurements.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<MeasurementDTO> paginatedMeasurements = measurements.subList(start, end);

        return new MeasurementsDTO(paginatedMeasurements, total, page);
    }

    private boolean checkSensorExists(UUID sensorId) {
        // Implement the logic to check if the sensor exists
        // For now, let's assume all sensors exist
        return true;
    }
}