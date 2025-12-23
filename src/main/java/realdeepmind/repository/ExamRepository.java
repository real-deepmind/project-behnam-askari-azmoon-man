package realdeepmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import realdeepmind.entity.Course;
import realdeepmind.entity.Exam;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByCourseIdOrderByStartDateAsc(Long courseId);

    List<Exam> findAllByOrderByStartDateAsc();

    @Query("select e from Exam e join e.course c join c.enrollments en " +
            "where en.user.id = :userId " +
            "order by e.startDate asc ")
    List<Exam> findAllExamsByUserId(@Param("userId") Long userId);

}
