package hydroguard.api.mock.controllers.sensors;

import hydroguard.api.mock.models.sensors.SensorDTO;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {
    private final List<SensorDTO> sensors = new ArrayList<>();

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
}