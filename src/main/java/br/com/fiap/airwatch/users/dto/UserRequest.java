package br.com.fiap.airwatch.users.dto;
import jakarta.validation.constraints.*;
public record UserRequest(
    Long cityId,
    @NotBlank @Size(max=150) String name,
    @NotBlank @Email @Size(max=200) String email,
    @NotBlank @Size(min=6,max=100) String password,
    String role, String phone) {}
