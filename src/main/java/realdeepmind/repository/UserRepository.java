package realdeepmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import realdeepmind.entity.User;
import realdeepmind.entity.enums.Role;
import realdeepmind.entity.enums.UserStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findByAttendedCourses_Id(Long attendedCoursesId);

    Optional<User> findByTaughtCourses_Id(Long taughtCoursesId);

    List<User> findByUserStatus(UserStatus userStatus);

    List<User> findByRole(Role role);

    List<User> findByRoleAndUserStatus(Role role, UserStatus userStatus);

}
