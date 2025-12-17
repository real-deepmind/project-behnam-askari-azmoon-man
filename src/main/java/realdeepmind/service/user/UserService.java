package realdeepmind.service.user;

import realdeepmind.entity.User;
import realdeepmind.entity.enums.Role;
import realdeepmind.entity.enums.UserStatus;

import java.util.List;
import java.util.Optional;


public interface UserService {
    User signUp(User user);

    User login(String username, String password);

    User updateUser(User user);

    void changePassword(Long userId, String oldPassword, String newPassword);


    void deleteById(Long id);

    Optional<User> findById(Long id);

    List<User> findAll();


    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findStudentsByCourseId(Long courseId);

    Optional<User> findTeacherByCourseId(Long courseId);

    List<User> findByUserStatus(UserStatus userStatus);

    List<User> findByRole(Role role);

    List<User> findByRoleAndUserStatus(Role role, UserStatus userStatus);

    List<User> searchUsers(String firstName, String lastName, Role role, UserStatus status);

    void changeUserStatus(Long userId, UserStatus newStatus);

    public void changeUserRole(Long userId, Role newRole);


}
