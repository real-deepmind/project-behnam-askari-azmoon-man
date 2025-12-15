package realdeepmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import realdeepmind.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {



}
