package realdeepmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import realdeepmind.entity.User;
import realdeepmind.entity.enums.Role;
import realdeepmind.entity.enums.UserStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query(
            "select u from User u join u.enrollments e " +
                    "where e.course.id = :courseId and e.roleInCourse = 'STUDENT'"
    )
    List<User> findStudentsByCourseId(@Param("courseId") Long courseId);

    @Query(
            "select u from User u join u.enrollments e " +
                    "where e.course.id = :courseId and e.roleInCourse = 'TEACHER'"
    )
    Optional<User> findTeacherByCourseId(@Param("courseId") Long courseId);


    List<User> findByUserStatus(UserStatus userStatus);

    List<User> findByRole(Role role);

    List<User> findByRoleAndUserStatus(Role role, UserStatus userStatus);

    List<User> findByFirstNameContainsIgnoreCaseAndLastNameContainingIgnoreCase(String firstName, String lastName);

    @Query("SELECT u FROM User u WHERE " +
            "(:firstName IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', CAST(:firstName AS string), '%'))) AND " +
            "(:lastName IS NULL OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', CAST(:lastName AS string), '%'))) AND " +
            "(:role IS NULL OR u.role = :role) AND " +
            "(:status IS NULL OR u.userStatus = :status)")
    List<User> searchUsers(@Param("firstName") String firstName,
                           @Param("lastName") String lastName,
                           @Param("role") Role role,
                           @Param("status") UserStatus status);


}
