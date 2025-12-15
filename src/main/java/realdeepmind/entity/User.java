package realdeepmind.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import realdeepmind.entity.enums.Role;
import realdeepmind.entity.enums.UserStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "firstname cannot be empty")
    @Column(nullable = false)
    private String firstName;
    @NotBlank(message = "lastname cannot be empty")
    @Column(nullable = false)
    private String lastName;
    @NotBlank(message = "username cannot be empty")
    @Column(nullable = false, unique = true)
    private String username;
    @NotBlank(message = "password cannot be empty")
    @Size(min = 6, max = 20)
    private String password;
    @NotNull(message = "role cannot be empty")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @NotNull(message = "status cannot be empty")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private String rejectionReason;
}
