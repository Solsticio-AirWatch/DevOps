package br.com.fiap.airwatch.airreading.api;
import br.com.fiap.airwatch.airreading.dto.*;
import br.com.fiap.airwatch.airreading.service.AirReadingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
@RestController @RequestMapping("/api/air-readings") @RequiredArgsConstructor @Tag(name="AirReading")
public class AirReadingController {
    private final AirReadingService service;
    @GetMapping @Operation(summary="List all air readings")
    public ResponseEntity<Page<AirReadingResponse>> findAll(@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findAll(p)); }
    @GetMapping("/{id}") @Operation(summary="Find reading by ID")
    public ResponseEntity<AirReadingResponse> findById(@PathVariable Long id) { return ResponseEntity.ok(service.findById(id)); }
    @GetMapping("/city/{cityId}") @Operation(summary="List readings by city")
    public ResponseEntity<Page<AirReadingResponse>> findByCity(@PathVariable Long cityId,@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findByCity(cityId,p)); }
    @PostMapping @Operation(summary="Create air reading")
    public ResponseEntity<AirReadingResponse> create(@RequestBody @Valid AirReadingRequest req) {
        var r=service.create(req);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(r.id()).toUri()).body(r);
    }
    @DeleteMapping("/{id}") @Operation(summary="Delete reading")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
