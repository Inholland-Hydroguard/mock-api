package hydroguard.api.mock.controllers.plants;

import hydroguard.api.mock.models.plants.PlantDTO;
import hydroguard.api.mock.models.plants.PlantsDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final List<PlantDTO> plants = new ArrayList<>();

    @PostConstruct
    public void init() {
        plants.add(new PlantDTO(UUID.randomUUID(), "Yellow Vine", "Plantae", "A yellow vine plant", getRandomDate(), getRandomDate()));
        plants.add(new PlantDTO(UUID.randomUUID(), "Blue Poppy", "Plantae", "A blue poppy plant", getRandomDate(), getRandomDate()));
        plants.add(new PlantDTO(UUID.randomUUID(), "Red Rose", "Plantae", "A red rose plant", getRandomDate(), getRandomDate()));
    }

    private LocalDateTime getRandomDate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastWeek = now.minusWeeks(1);
        long randomEpochSecond = ThreadLocalRandom.current().nextLong(lastWeek.toEpochSecond(ZoneOffset.UTC), now.toEpochSecond(ZoneOffset.UTC));
        return LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneOffset.UTC);
    }

    @PostMapping
    public PlantDTO createPlant(@RequestBody PlantDTO plantDTO) {
        plantDTO.setId(UUID.randomUUID());
        plants.add(plantDTO);
        return plantDTO;
    }

    @GetMapping
    public PlantsDTO getAllPlants(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDateTime created,
            @RequestParam(required = false) LocalDateTime updated) {


        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }
        if (pageSize < 1 || pageSize > 100) {
            throw new IllegalArgumentException("Page size must be between 1 and 100");
        }


        List<PlantDTO> filteredPlants = plants;

        if (name != null && !name.isEmpty()) {
            filteredPlants = filteredPlants.stream()
                    .filter(plant -> plant.getName().contains(name) || plant.getSpecies().contains(name))
                    .collect(Collectors.toList());
        }

        if (created != null) {
            filteredPlants = filteredPlants.stream()
                    .filter(plant -> plant.getCreatedAt().toLocalDate().equals(created.toLocalDate()))
                    .collect(Collectors.toList());
        }

        if (updated != null) {
            filteredPlants = filteredPlants.stream()
                    .filter(plant -> plant.getUpdatedAt().toLocalDate().equals(updated.toLocalDate()))
                    .collect(Collectors.toList());
        }

        int total = filteredPlants.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);

        List<PlantDTO> paginatedPlants = filteredPlants.subList(start, end);

        return new PlantsDTO(paginatedPlants, total, page);
    }

    @GetMapping("/{id}")
    public PlantDTO getPlantById(@PathVariable UUID id) {
        return plants.stream()
                .filter(plant -> plant.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plant not found"));
    }

    @PutMapping("/{id}")
    public PlantDTO updatePlant(@PathVariable UUID id, @RequestBody PlantDTO updatedPlant) {
        for (PlantDTO plant : plants) {
            if (plant.getId().equals(id)) {
                plant.setName(updatedPlant.getName());
                plant.setSpecies(updatedPlant.getSpecies());
                plant.setDescription(updatedPlant.getDescription());
                plant.setUpdatedAt(updatedPlant.getUpdatedAt());
                return plant;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deletePlant(@PathVariable UUID id) {
        plants.removeIf(plant -> plant.getId().equals(id));
    }
}