package br.com.fiap.airwatch.country.api;
import br.com.fiap.airwatch.country.dto.*;
import br.com.fiap.airwatch.country.service.CountryService;
import io.swagger.v3.oas.annotations.Operation; import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*; import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
@RestController @RequestMapping("/api/countries") @RequiredArgsConstructor @Tag(name="Country")
public class CountryController {
    private final CountryService service;
    @GetMapping @Operation(summary="List all countries")
    public ResponseEntity<Page<CountryResponse>> findAll(@PageableDefault(size=20,sort="name") Pageable p){return ResponseEntity.ok(service.findAll(p));}
    @GetMapping("/{id}") @Operation(summary="Find by ID")
    public ResponseEntity<CountryResponse> findById(@PathVariable Long id){return ResponseEntity.ok(service.findById(id));}
    @PostMapping @Operation(summary="Create country")
    public ResponseEntity<CountryResponse> create(@RequestBody @Valid CountryRequest req){
        var r=service.create(req);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(r.id()).toUri()).body(r);
    }
    @PutMapping("/{id}") @Operation(summary="Update country")
    public ResponseEntity<CountryResponse> update(@PathVariable Long id,@RequestBody @Valid CountryRequest req){return ResponseEntity.ok(service.update(id,req));}
    @DeleteMapping("/{id}") @Operation(summary="Delete country")
    public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
}
