package realdeepmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import realdeepmind.entity.Course;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);

    boolean existsByCourseCode(String courseCode);

    List<Course> findByTeacherId(Long teacherId);

    List<Course> findByStudentsId(Long studentsId);

    List<Course> findByTeacherIdAndStudentsId(Long teacherId, Long studentsId);

    List<Course> findByTitleContainingIgnoreCase(String title);

    List<Course> findByStartDateAfter(LocalDate startDate);

    List<Course> findByEndDateBefore(LocalDate endDate);

    List<Course> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);

    List<Course> findByDurationGreaterThan(Integer duration);

    List<Course> findByDurationLessThan(Integer duration);


}
