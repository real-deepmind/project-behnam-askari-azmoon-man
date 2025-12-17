package realdeepmind.dto.user;

import lombok.Builder;
import lombok.Data;
import realdeepmind.entity.enums.Role;
import realdeepmind.entity.enums.UserStatus;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private Role role;
    private UserStatus status;
}