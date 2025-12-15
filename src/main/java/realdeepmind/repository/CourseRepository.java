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

    List<Course> findByTeacher_Id(Long teacherId);

    List<Course> findByStudents_Id(Long studentsId);

    List<Course> findByTeacher_IdAndStudents_Id(Long teacherId, Long studentsId);

    List<Course> findByTitleContainingIgnoreCase(String title);

    List<Course> findByStartDateAfter(LocalDate startDate);

    List<Course> findByEndDateBefore(LocalDate endDate);

    List<Course> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);

    List<Course> findByDurationGreaterThan(Integer duration);

    List<Course> findByDurationLessThan(Integer duration);


}
