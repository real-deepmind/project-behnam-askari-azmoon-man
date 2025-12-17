package realdeepmind.mapper;

import org.springframework.stereotype.Component;
import realdeepmind.dto.user.UserRegisterDto;
import realdeepmind.dto.user.UserResponseDto;
import realdeepmind.entity.User;

@Component
public class UserMapper {

    public User toEntity(UserRegisterDto dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    public UserResponseDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .role(user.getRole())
                .status(user.getUserStatus())
                .build();
    }
}
