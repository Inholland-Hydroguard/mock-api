package hydroguard.api.mock.controllers.sensors;

import hydroguard.api.mock.models.sensors.SensorDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {
    private final List<SensorDTO> sensors = new ArrayList<>();

    @PostConstruct
    public void init() {
        UUID userId = UUID.fromString("0000000-0000-0000-0000-000000000000");
        sensors.add(new SensorDTO(UUID.randomUUID(), userId, UUID.randomUUID(), "1 Sensor", 10.0f, 30.0f, (byte) 100, true, true, LocalDateTime.now(), LocalDateTime.now()));
        sensors.add(new SensorDTO(UUID.randomUUID(), userId, UUID.randomUUID(), "2 Sensor", 20.0f, 70.0f, (byte) 80, false, true, LocalDateTime.now(), LocalDateTime.now()));
        sensors.add(new SensorDTO(UUID.randomUUID(), userId, UUID.randomUUID(), "3 Sensor", 100.0f, 1000.0f, (byte) 90, true, false, LocalDateTime.now(), LocalDateTime.now()));
    }

    @GetMapping("/{id}")
    public SensorDTO getSensorById(@PathVariable UUID id) {
        return sensors.stream().filter(sensor -> sensor.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    public SensorDTO createSensor(@RequestBody SensorDTO sensor) {
        sensor.setId(UUID.randomUUID());
        sensors.add(sensor);
        return sensor;
    }

    @PutMapping("/{id}")
    public SensorDTO updateSensor(@PathVariable UUID id, @RequestBody SensorDTO updatedSensor) {
        SensorDTO sensor = sensors.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
        if (sensor != null) {
            sensor.setHouseholdId(updatedSensor.getHouseholdId());
            sensor.setPlantId(updatedSensor.getPlantId());
            sensor.setName(updatedSensor.getName());
            sensor.setThresholdMin(updatedSensor.getThresholdMin());
            sensor.setThresholdMax(updatedSensor.getThresholdMax());
            sensor.setIsOutdoor(updatedSensor.getIsOutdoor());
            sensor.setIsActive(updatedSensor.getIsActive());
            sensor.setUpdatedAt(LocalDateTime.now());
        }
        return sensor;
    }

    @DeleteMapping("/{id}")
    public void deleteSensor(@PathVariable UUID id) {
        sensors.removeIf(sensor -> sensor.getId().equals(id));
    }

    @GetMapping("/household/{householdId}")
    public List<SensorDTO> getAllSensorsByUser(@PathVariable UUID householdId) {
        return sensors.stream()
                .filter(sensor -> sensor.getHouseholdId().equals(householdId))
                .collect(Collectors.toList());
    }
}