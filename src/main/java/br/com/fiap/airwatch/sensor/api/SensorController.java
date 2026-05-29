package br.com.fiap.airwatch.sensor.api;
import br.com.fiap.airwatch.sensor.dto.*;
import br.com.fiap.airwatch.sensor.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
@RestController @RequestMapping("/api/sensors") @RequiredArgsConstructor @Tag(name="Sensor")
public class SensorController {
    private final SensorService service;
    @GetMapping @Operation(summary="List all sensors")
    public ResponseEntity<Page<SensorResponse>> findAll(@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findAll(p)); }
    @GetMapping("/{id}") @Operation(summary="Find sensor by ID")
    public ResponseEntity<SensorResponse> findById(@PathVariable Long id) { return ResponseEntity.ok(service.findById(id)); }
    @GetMapping("/city/{cityId}") @Operation(summary="List sensors by city")
    public ResponseEntity<Page<SensorResponse>> findByCity(@PathVariable Long cityId,@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findByCity(cityId,p)); }
    @PostMapping @Operation(summary="Create sensor")
    public ResponseEntity<SensorResponse> create(@RequestBody @Valid SensorRequest req) {
        var r=service.create(req);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(r.id()).toUri()).body(r);
    }
    @PutMapping("/{id}") @Operation(summary="Update sensor")
    public ResponseEntity<SensorResponse> update(@PathVariable Long id,@RequestBody @Valid SensorRequest req) { return ResponseEntity.ok(service.update(id,req)); }
    @DeleteMapping("/{id}") @Operation(summary="Delete sensor")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
