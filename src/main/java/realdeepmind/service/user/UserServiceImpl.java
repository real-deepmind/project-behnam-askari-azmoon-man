package realdeepmind.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realdeepmind.entity.User;
import realdeepmind.entity.enums.Role;
import realdeepmind.entity.enums.UserStatus;
import realdeepmind.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User signUp(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username is already taken: " + user.getUsername());
        }
        if (user.getUserStatus() == null) {
            user.setUserStatus(UserStatus.WAITING);
        }
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password.");
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found."));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        return userRepository.save(existingUser);
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Invalid old password.");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
    }


    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public List<User> findStudentsByCourseId(Long courseId) {
        return userRepository.findStudentsByCourseId(courseId);
    }

    @Override
    public Optional<User> findTeacherByCourseId(Long courseId) {
        return userRepository.findTeacherByCourseId(courseId);
    }

    @Override
    public List<User> findByUserStatus(UserStatus userStatus) {
        return userRepository.findByUserStatus(userStatus);
    }

    @Override
    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> findByRoleAndUserStatus(Role role, UserStatus userStatus) {
        return userRepository.findByRoleAndUserStatus(role, userStatus);
    }

    @Override
    public List<User> searchUsers(String firstName, String lastName, Role role, UserStatus status) {
        return userRepository.searchUsers(firstName, lastName, role, status);
    }

    @Override
    public void changeUserStatus(Long userId, UserStatus newStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUserStatus(newStatus);
        userRepository.save(user);
    }

    @Override
    public void changeUserRole(Long userId, Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(newRole);
        userRepository.save(user);
    }


}