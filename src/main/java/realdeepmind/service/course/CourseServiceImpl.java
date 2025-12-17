package realdeepmind.service.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realdeepmind.entity.Course;
import realdeepmind.repository.CourseRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public Course createCourse(Course course) {
        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new RuntimeException("Course code already exists: " + course.getCourseCode());
        }
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course course) {
        Course existingCourse = courseRepository.findById(course.getId())
                .orElseThrow(() -> new RuntimeException("Course not found."));

        existingCourse.setTitle(course.getTitle());
        existingCourse.setStartDate(course.getStartDate());
        existingCourse.setEndDate(course.getEndDate());
        existingCourse.setDuration(course.getDuration());

        return courseRepository.save(existingCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found.");
        }
        courseRepository.deleteById(id);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found."));
    }

    @Override
    public Course getCourseByCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found with code: " + courseCode));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getCoursesByTeacher(Long teacherId) {
        return courseRepository.findCoursesByTeacherId(teacherId);
    }

    @Override
    public List<Course> getCoursesByStudent(Long studentId) {
        return courseRepository.findCoursesByStudentId(studentId);
    }

    @Override
    public List<Course> getCoursesWithNoEnrollments() {
        return courseRepository.findCoursesWithNoEnrollments();
    }

    @Override
    public List<Course> searchByTitle(String title) {
        return courseRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Course> filterByStartDateAfter(LocalDate date) {
        return courseRepository.findByStartDateAfter(date);
    }

    @Override
    public List<Course> filterByEndDateBefore(LocalDate date) {
        return courseRepository.findByEndDateBefore(date);
    }

    @Override
    public List<Course> filterByDurationGreaterThan(Integer duration) {
        return courseRepository.findByDurationGreaterThan(duration);
    }
}
