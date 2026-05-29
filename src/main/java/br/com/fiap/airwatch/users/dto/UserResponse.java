package br.com.fiap.airwatch.users.dto;
import br.com.fiap.airwatch.users.model.Users;
import java.time.LocalDateTime;
public record UserResponse(Long id,Long cityId,String cityName,String name,String email,
    String role,String phone,String isActive,LocalDateTime createdAt,LocalDateTime lastLoginAt) {
    public static UserResponse from(Users u) {
        return new UserResponse(u.getId(),
            u.getCity()!=null?u.getCity().getId():null,
            u.getCity()!=null?u.getCity().getName():null,
            u.getName(),u.getEmail(),u.getRole(),u.getPhone(),
            u.getIsActive(),u.getCreatedAt(),u.getLastLoginAt());
    }
}
