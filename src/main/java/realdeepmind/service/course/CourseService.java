package realdeepmind.service.course;

import realdeepmind.entity.Course;

import java.time.LocalDate;
import java.util.List;

public interface CourseService {

    Course createCourse(Course course);

    Course updateCourse(Course course);

    void deleteCourse(Long id);

    Course getCourseById(Long id);

    Course getCourseByCode(String courseCode);

    List<Course> getAllCourses();

    List<Course> getCoursesByTeacher(Long teacherId);

    List<Course> getCoursesByStudent(Long studentId);

    List<Course> getCoursesWithNoEnrollments();

    List<Course> searchByTitle(String title);

    List<Course> filterByStartDateAfter(LocalDate date);

    List<Course> filterByEndDateBefore(LocalDate date);

    List<Course> filterByDurationGreaterThan(Integer duration);
}
