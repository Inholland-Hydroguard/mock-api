package hydroguard.api.mock.controllers.measurements;

import hydroguard.api.mock.models.measurements.MeasurementDTO;
import hydroguard.api.mock.models.measurements.MeasurementsDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class MeasurementController {

    // Retrieve the latest measurement for a specific sensor
    @GetMapping("/measurement/{sensorId}")
    public MeasurementDTO getLatestMeasurement(@PathVariable UUID sensorId) {
        // Logic to retrieve the latest measurement for the sensor
        return new MeasurementDTO(sensorId);
    }

    // Retrieve the latest measurements for a specific sensor
    @GetMapping("/measurements/{sensorId}")
    public MeasurementsDTO getLatestMeasurements(@PathVariable UUID sensorId) {
        // Logic to retrieve the latest measurements for the sensor
        List<MeasurementDTO> measurements = new ArrayList<>();
        measurements.add(new MeasurementDTO(sensorId));
        measurements.add(new MeasurementDTO(sensorId));
        measurements.add(new MeasurementDTO(sensorId));
        measurements.add(new MeasurementDTO(sensorId));
        measurements.add(new MeasurementDTO(sensorId));

        MeasurementsDTO measurementsDTO = new MeasurementsDTO();
        measurementsDTO.setData(measurements);
        measurementsDTO.setTotal(measurements.size());
        measurementsDTO.setCurrentPage(1);

        return measurementsDTO;
    }
}