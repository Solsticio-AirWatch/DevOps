package br.com.fiap.airwatch.integrationlog.api;
import br.com.fiap.airwatch.integrationlog.dto.*;
import br.com.fiap.airwatch.integrationlog.service.IntegrationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
@RestController @RequestMapping("/api/integration-logs") @RequiredArgsConstructor @Tag(name="IntegrationLog")
public class IntegrationLogController {
    private final IntegrationLogService service;
    @GetMapping @Operation(summary="List all integration logs")
    public ResponseEntity<Page<IntegrationLogResponse>> findAll(@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findAll(p)); }
    @GetMapping("/{id}") @Operation(summary="Find by ID")
    public ResponseEntity<IntegrationLogResponse> findById(@PathVariable Long id) { return ResponseEntity.ok(service.findById(id)); }
    @GetMapping("/api-name/{apiName}") @Operation(summary="List by API name")
    public ResponseEntity<Page<IntegrationLogResponse>> findByApiName(@PathVariable String apiName,@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findByApiName(apiName,p)); }
    @GetMapping("/result/{result}") @Operation(summary="List by result")
    public ResponseEntity<Page<IntegrationLogResponse>> findByResult(@PathVariable String result,@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findByResult(result,p)); }
    @GetMapping("/city/{cityId}") @Operation(summary="List by city")
    public ResponseEntity<Page<IntegrationLogResponse>> findByCity(@PathVariable Long cityId,@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findByCity(cityId,p)); }
    @GetMapping("/errors") @Operation(summary="List all failed calls")
    public ResponseEntity<Page<IntegrationLogResponse>> findAllErrors(@PageableDefault(size=20) Pageable p) { return ResponseEntity.ok(service.findAllErrors(p)); }
    @PostMapping @Operation(summary="Create integration log")
    public ResponseEntity<IntegrationLogResponse> create(@RequestBody @Valid IntegrationLogRequest req) {
        var r=service.create(req);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(r.id()).toUri()).body(r);
    }
    @DeleteMapping("/{id}") @Operation(summary="Delete integration log")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
