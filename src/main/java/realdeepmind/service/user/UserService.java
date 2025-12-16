package realdeepmind.service.user;

import realdeepmind.entity.User;
import realdeepmind.entity.enums.Role;
import realdeepmind.entity.enums.UserStatus;

import java.util.List;
import java.util.Optional;


public interface UserService {
    User save(User user);

    Optional<User> login(String username, String password);

    void verifyUser(Long userId);

    void rejectUser(Long userId, String rejectionReason);

    User update(User user);

    void delete(Long id);

    boolean existsByUsername(String username);

    User findById(Long id);

    User findByUsername(String username);

    List<User> findAllUsers();

    List<User> findUsersByRole(Role role);

    List<User> findUsersByStatus(UserStatus status);

    List<User> findUsersByRoleAndStatus(Role role, UserStatus status);

    List<User> findStudentsOfCourse(Long courseId);

    User findTeacherOfCourse(Long courseId);

}
