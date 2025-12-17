package realdeepmind.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import realdeepmind.entity.CourseEnrollment;
import realdeepmind.entity.enums.RoleInCourse;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseEnrollmentRepository {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    Optional<CourseEnrollment> findByUserIdAndCourseId(Long userId, Long courseId);

    List<CourseEnrollment> findByUserId(Long userId);

    List<CourseEnrollment> findByCourseId(Long courseId);

    @Query("SELECT ce FROM CourseEnrollment ce WHERE " +
            "(:courseId IS NULL OR ce.course.id = :courseId) AND " +
            "(:userId IS NULL OR ce.user.id = :userId) AND " +
            "(:role IS NULL OR ce.roleInCourse = :role)")
    List<CourseEnrollment> searchEnrollments(@Param("courseId") Long courseId,
                                             @Param("userId") Long userId,
                                             @Param("role") RoleInCourse role);
}
