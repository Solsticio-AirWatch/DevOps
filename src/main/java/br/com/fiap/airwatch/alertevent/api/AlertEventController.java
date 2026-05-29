package br.com.fiap.airwatch.alertevent.api;
import br.com.fiap.airwatch.alertevent.dto.*;
import br.com.fiap.airwatch.alertevent.service.AlertEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
@RestController @RequestMapping("/api/alert-events") @RequiredArgsConstructor @Tag(name="AlertEvent")
public class AlertEventController {
    private final AlertEventService service;
    @GetMapping @Operation(summary="List all alert events")
    public ResponseEntity<Page<AlertEventResponse>> findAll(@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findAll(p)); }
    @GetMapping("/{id}") @Operation(summary="Find alert event by ID")
    public ResponseEntity<AlertEventResponse> findById(@PathVariable Long id) { return ResponseEntity.ok(service.findById(id)); }
    @GetMapping("/city/{cityId}") @Operation(summary="List by city")
    public ResponseEntity<Page<AlertEventResponse>> findByCity(@PathVariable Long cityId,@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findByCity(cityId,p)); }
    @GetMapping("/user/{userId}") @Operation(summary="List by user")
    public ResponseEntity<Page<AlertEventResponse>> findByUser(@PathVariable Long userId,@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findByUser(userId,p)); }
    @GetMapping("/status/{status}") @Operation(summary="List by status")
    public ResponseEntity<Page<AlertEventResponse>> findByStatus(@PathVariable String status,@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findByStatus(status,p)); }
    @PostMapping @Operation(summary="Create alert event")
    public ResponseEntity<AlertEventResponse> create(@RequestBody @Valid AlertEventRequest req) {
        var r=service.create(req);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(r.id()).toUri()).body(r);
    }
    @PatchMapping("/{id}/send") @Operation(summary="Mark as SENT")
    public ResponseEntity<AlertEventResponse> markAsSent(@PathVariable Long id) { return ResponseEntity.ok(service.markAsSent(id)); }
    @PatchMapping("/{id}/ignore") @Operation(summary="Mark as IGNORED")
    public ResponseEntity<AlertEventResponse> markAsIgnored(@PathVariable Long id) { return ResponseEntity.ok(service.markAsIgnored(id)); }
    @DeleteMapping("/{id}") @Operation(summary="Delete alert event")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
