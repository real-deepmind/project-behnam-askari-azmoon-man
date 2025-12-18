package realdeepmind.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import realdeepmind.dto.user.ChangePasswordRequest;
import realdeepmind.dto.user.UserRegisterDto;
import realdeepmind.dto.user.UserResponseDto;
import realdeepmind.dto.user.UserUpdateDto;
import realdeepmind.entity.User;
import realdeepmind.entity.enums.Role;
import realdeepmind.entity.enums.UserStatus;
import realdeepmind.mapper.UserMapper;
import realdeepmind.service.user.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/waiting")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getWaitingUsers() {
        List<User> users = userService.findByUserStatus(UserStatus.WAITING);
        return ResponseEntity.ok(users.stream().map(userMapper::toDto).collect(Collectors.toList()));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(
            @PathVariable Long id,
            @RequestParam UserStatus status,
            @RequestParam(required = false) String reason
    ) {
        userService.changeUserStatus(id, status, reason);
        return ResponseEntity.ok("User status updated to " + status);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDto userDto
    ) {

        User user = userService.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeRole(@PathVariable Long id, @RequestParam Role role) {
        userService.changeUserRole(id, role);
        return ResponseEntity.ok("User role updated to " + role);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> searchUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) UserStatus status
    ) {
        List<User> users = userService.searchUsers(firstName, lastName, role, status);
        return ResponseEntity.ok(users.stream().map(userMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> createUserByAdmin(@Valid @RequestBody UserRegisterDto registerDto) {
        User user = userMapper.toEntity(registerDto);
        user.setUserStatus(UserStatus.APPROVED);
        User savedUser = userService.signUp(user);
        return ResponseEntity.ok(userMapper.toDto(savedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userService.deleteById(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getMyProfile(Principal principal) {
        String currentUsername = principal.getName();

        User currentUser = userService.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        return ResponseEntity.ok(userMapper.toDto(currentUser));
    }


    @PutMapping("/profile")
    public ResponseEntity<UserResponseDto> updateMyProfile(
            @Valid @RequestBody UserUpdateDto userDto,
            Principal principal
    ) {
        String currentUsername = principal.getName();
        User currentUser = userService.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        currentUser.setFirstName(userDto.getFirstName());
        currentUser.setLastName(userDto.getLastName());

        User updatedUser = userService.updateUser(currentUser);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            Principal principal
    ) {
        String currentUsername = principal.getName();
        User currentUser = userService.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userService.changePassword(
                currentUser.getId(),
                request.getOldPassword(),
                request.getNewPassword()
        );

        return ResponseEntity.ok("Password changed successfully.");
    }
}