package hydroguard.api.mock.controllers.plants;

import hydroguard.api.mock.models.plants.PlantDTO;
import hydroguard.api.mock.models.plants.PlantsDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
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

    @PostMapping(consumes = "multipart/form-data")
    public PlantDTO createPlant(
            @RequestParam("name") String name,
            @RequestParam("householdId") UUID householdId,
            @RequestParam("species") String species,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) {

        UUID id = UUID.randomUUID();

        PlantDTO plantDTO = new PlantDTO(id, householdId, name, species, description, LocalDateTime.now(), LocalDateTime.now());
        plants.add(plantDTO);
        return plantDTO;
    }

    // sort uses the var names from the responses
    @GetMapping
    public PlantsDTO getAllPlants(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String householdId,
            @RequestParam(required = false) LocalDateTime createdFrom,
            @RequestParam(required = false) LocalDateTime createdTo,
            @RequestParam(required = false) LocalDateTime updatedFrom,
            @RequestParam(required = false) LocalDateTime updatedTo,
            @RequestParam(required = false) String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }
        if (pageSize < 1 || pageSize > 100) {
            throw new IllegalArgumentException("Page size must be between 1 and 100");
        }

        List<PlantDTO> filteredPlants = plants;

        if (householdId != null && !householdId.isEmpty()) {
            filteredPlants = filteredPlants.stream()
                    .filter(plant -> plant.getHouseholdId().equals(UUID.fromString(householdId)))
                    .collect(Collectors.toList());
        }

        if (name != null && !name.isEmpty()) {
            filteredPlants = filteredPlants.stream()
                    .filter(plant -> plant.getName().contains(name) || plant.getSpecies().contains(name))
                    .collect(Collectors.toList());
        }

        if (createdFrom != null) {
            filteredPlants = filteredPlants.stream()
                    .filter(plant -> !plant.getCreatedAt().isBefore(createdFrom))
                    .collect(Collectors.toList());
        }

        if (createdTo != null) {
            filteredPlants = filteredPlants.stream()
                    .filter(plant -> !plant.getCreatedAt().isAfter(createdTo))
                    .collect(Collectors.toList());
        }

        if (updatedFrom != null) {
            filteredPlants = filteredPlants.stream()
                    .filter(plant -> !plant.getUpdatedAt().isBefore(updatedFrom))
                    .collect(Collectors.toList());
        }

        if (updatedTo != null) {
            filteredPlants = filteredPlants.stream()
                    .filter(plant -> !plant.getUpdatedAt().isAfter(updatedTo))
                    .collect(Collectors.toList());
        }

        if (sortField != null) {
            Comparator<PlantDTO> comparator;
            switch (sortField) {
                case "name":
                    comparator = Comparator.comparing(PlantDTO::getName);
                    break;
                case "createdAt":
                    comparator = Comparator.comparing(PlantDTO::getCreatedAt);
                    break;
                case "updatedAt":
                    comparator = Comparator.comparing(PlantDTO::getUpdatedAt);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sort field");
            }

            if ("desc".equalsIgnoreCase(sortDirection)) {
                comparator = comparator.reversed();
            }

            filteredPlants.sort(comparator);
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
