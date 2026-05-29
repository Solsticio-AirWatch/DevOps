package br.com.fiap.airwatch.city.api;
import br.com.fiap.airwatch.city.dto.*; import br.com.fiap.airwatch.city.service.CityService;
import io.swagger.v3.oas.annotations.Operation; import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*; import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
@RestController @RequestMapping("/api/cities") @RequiredArgsConstructor @Tag(name="City")
public class CityController {
    private final CityService service;
    @GetMapping @Operation(summary="List all cities")
    public ResponseEntity<Page<CityResponse>> findAll(@PageableDefault(size=20,sort="name") Pageable p){return ResponseEntity.ok(service.findAll(p));}
    @GetMapping("/{id}") @Operation(summary="Find by ID")
    public ResponseEntity<CityResponse> findById(@PathVariable Long id){return ResponseEntity.ok(service.findById(id));}
    @GetMapping("/country/{cid}") @Operation(summary="List by country")
    public ResponseEntity<Page<CityResponse>> findByCountry(@PathVariable Long cid,@PageableDefault(size=20) Pageable p){return ResponseEntity.ok(service.findByCountry(cid,p));}
    @PostMapping @Operation(summary="Create city")
    public ResponseEntity<CityResponse> create(@RequestBody @Valid CityRequest req){
        var r=service.create(req);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(r.id()).toUri()).body(r);
    }
    @PutMapping("/{id}") @Operation(summary="Update city")
    public ResponseEntity<CityResponse> update(@PathVariable Long id,@RequestBody @Valid CityRequest req){return ResponseEntity.ok(service.update(id,req));}
    @DeleteMapping("/{id}") @Operation(summary="Delete city")
    public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
}
