package hydroguard.api.mock.models.measurements;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class MeasurementDTO {
    private UUID sensorId;
    private Float moisturePercentage;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    public MeasurementDTO(UUID sensorId, Float moisturePercentage, LocalDateTime date) {
        this.sensorId = sensorId;
        this.moisturePercentage = moisturePercentage;
        this.date = date;
    }

    public MeasurementDTO(UUID sensorId) {
        this.sensorId = sensorId;
        this.moisturePercentage = new Random().nextFloat();
        this.date = LocalDateTime.now();
    }

    public MeasurementDTO() {
        this.sensorId = UUID.randomUUID();
        this.moisturePercentage = new Random().nextFloat();
        this.date = LocalDateTime.now();
    }

    public Float getMoisturePercentage() {
        return moisturePercentage;
    }

    public void setMoisturePercentage(Float moisturePercentage) {
        this.moisturePercentage = moisturePercentage;
    }

    public UUID getSensorId() {
        return sensorId;
    }

    public void setSensorId(UUID sensorId) {
        this.sensorId = sensorId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}