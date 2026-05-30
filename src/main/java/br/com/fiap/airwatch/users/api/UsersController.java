package br.com.fiap.airwatch.users.api;
import br.com.fiap.airwatch.users.dto.*;
import br.com.fiap.airwatch.users.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users")
public class UsersController {

    private final UsersService service;
    private final AuthenticationManager authManager;

    @PostMapping("/api/auth/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest req) {
        var r = service.create(req);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/users/{id}").buildAndExpand(r.id()).toUri()
        ).body(r);
    }

    @PostMapping("/api/auth/login")
    @Operation(summary = "Login and get JWT token")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        return ResponseEntity.ok(service.buildAuthResponse(req.email()));
    }

    @GetMapping("/api/users")
    @Operation(summary = "List all users")
    public ResponseEntity<Page<UserResponse>> findAll(Pageable p) {
        return ResponseEntity.ok(service.findAll(p));
    }

    @GetMapping("/api/users/{id}")
    @Operation(summary = "Find user by ID")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/api/users/{id}")
    @Operation(summary = "Update user")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @RequestBody @Valid UserRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/api/users/{id}")
    @Operation(summary = "Delete user")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}