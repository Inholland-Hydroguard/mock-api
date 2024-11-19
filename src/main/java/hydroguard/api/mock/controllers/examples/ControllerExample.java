package hydroguard.api.mock.controllers.examples;

import hydroguard.api.mock.models.examples.CreateModelExampleDTO;
import hydroguard.api.mock.models.examples.GetModelExampleDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ControllerExample {

    // Example GET endpoint
    @GetMapping("/examples")
    public List<GetModelExampleDTO> getItems() {
        List<GetModelExampleDTO> mockData = new ArrayList<>();
        mockData.add(new GetModelExampleDTO(UUID.randomUUID(), "Item One"));
        mockData.add(new GetModelExampleDTO(UUID.randomUUID(), "Item Two"));
        return mockData;
    }

    // Example GET endpoint
    @GetMapping("/examples/{id}")
    public GetModelExampleDTO getItem(@PathVariable UUID id) {
        return new GetModelExampleDTO(id, "Item One");
    }

    // Example POST endpoint
    @PostMapping("/examples")
    public GetModelExampleDTO createItem(@RequestBody CreateModelExampleDTO item) {
        return new GetModelExampleDTO(UUID.randomUUID(), item.getName());
    }

    // Example DELETE endpoint
    @DeleteMapping("/examples/{id}")
    public Map<String, String> deleteItem(@PathVariable UUID id) {
        return Map.of("status", "success", "message", "Item with ID " + id + " deleted");
    }

    // Example PUT endpoint
    @PutMapping("/examples/{id}")
    public GetModelExampleDTO updateItem(@PathVariable UUID id, @RequestBody CreateModelExampleDTO updatedItem) {
        return new GetModelExampleDTO(id, updatedItem.getName());
    }
}
