package realdeepmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import realdeepmind.entity.Course;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);

    boolean existsByCourseCode(String courseCode);

    @Query("SELECT c FROM Course c JOIN c.enrollments e WHERE e.user.id = :teacherId AND e.roleInCourse = 'TEACHER'")
    List<Course> findCoursesByTeacherId(@Param("teacherId") Long teacherId);

    @Query(
            value = "SELECT c.* FROM courses c " +
                    "INNER JOIN course_enrollments ce ON c.id = ce.course_id " +
                    "WHERE ce.user_id = :userId AND ce.role_in_course = 'STUDENT'",
            nativeQuery = true
    )
    List<Course> findCoursesByStudentId(@Param("userId") Long userId);

    List<Course> findByTitleContainingIgnoreCase(String title);

    List<Course> findByStartDateAfter(LocalDate startDate);

    List<Course> findByEndDateBefore(LocalDate endDate);

    List<Course> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);

    List<Course> findByDurationGreaterThan(Integer duration);

    List<Course> findByDurationLessThan(Integer duration);


}
