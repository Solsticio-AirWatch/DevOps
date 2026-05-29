package br.com.fiap.airwatch.users.dto;
import jakarta.validation.constraints.*;
public record AuthRequest(@NotBlank @Email String email, @NotBlank String password) {}
