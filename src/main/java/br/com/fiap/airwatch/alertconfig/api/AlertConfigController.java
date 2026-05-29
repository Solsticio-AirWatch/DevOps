package br.com.fiap.airwatch.alertconfig.api;
import br.com.fiap.airwatch.alertconfig.dto.*;
import br.com.fiap.airwatch.alertconfig.service.AlertConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
@RestController @RequestMapping("/api/alert-configs") @RequiredArgsConstructor @Tag(name="AlertConfig")
public class AlertConfigController {
    private final AlertConfigService service;
    @GetMapping @Operation(summary="List all alert configs")
    public ResponseEntity<Page<AlertConfigResponse>> findAll(@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findAll(p)); }
    @GetMapping("/{id}") @Operation(summary="Find alert config by ID")
    public ResponseEntity<AlertConfigResponse> findById(@PathVariable Long id) { return ResponseEntity.ok(service.findById(id)); }
    @GetMapping("/user/{userId}") @Operation(summary="List by user")
    public ResponseEntity<Page<AlertConfigResponse>> findByUser(@PathVariable Long userId,@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findByUser(userId,p)); }
    @GetMapping("/city/{cityId}") @Operation(summary="List by city")
    public ResponseEntity<Page<AlertConfigResponse>> findByCity(@PathVariable Long cityId,@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findByCity(cityId,p)); }
    @PostMapping @Operation(summary="Create alert config")
    public ResponseEntity<AlertConfigResponse> create(@RequestBody @Valid AlertConfigRequest req) {
        var r=service.create(req);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(r.id()).toUri()).body(r);
    }
    @PutMapping("/{id}") @Operation(summary="Update alert config")
    public ResponseEntity<AlertConfigResponse> update(@PathVariable Long id,@RequestBody @Valid AlertConfigRequest req) { return ResponseEntity.ok(service.update(id,req)); }
    @PatchMapping("/{id}/toggle") @Operation(summary="Toggle active/inactive")
    public ResponseEntity<AlertConfigResponse> toggle(@PathVariable Long id) { return ResponseEntity.ok(service.toggle(id)); }
    @DeleteMapping("/{id}") @Operation(summary="Delete alert config")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
