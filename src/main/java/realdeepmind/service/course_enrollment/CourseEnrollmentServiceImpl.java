package realdeepmind.service.course_enrollment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realdeepmind.entity.Course;
import realdeepmind.entity.CourseEnrollment;
import realdeepmind.entity.User;
import realdeepmind.entity.enums.RoleInCourse;
import realdeepmind.repository.CourseEnrollmentRepository;
import realdeepmind.repository.CourseRepository;
import realdeepmind.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    private final CourseEnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Transactional
    @Override
    public CourseEnrollment enroll(Long userId, Long courseId, RoleInCourse role) {
        if (enrollmentRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new RuntimeException("User is already enrolled in this course.");
        }

        if (role == RoleInCourse.TEACHER) {
            if (userRepository.findTeacherByCourseId(courseId).isPresent()) {
                throw new RuntimeException("Error: This course already has a teacher.");
            }
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found."));

        CourseEnrollment enrollment = CourseEnrollment.builder()
                .user(user)
                .course(course)
                .roleInCourse(role)
                .enrollmentDate(LocalDateTime.now())
                .build();

        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    @Override
    public CourseEnrollment assignGrade(Long userId, Long courseId, Double grade) {
        CourseEnrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found for this user and course."));

        if (enrollment.getRoleInCourse() != RoleInCourse.STUDENT) {
            throw new RuntimeException("Grades can only be assigned to students.");
        }

        enrollment.setGrade(grade);
        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    @Override
    public void unenroll(Long userId, Long courseId) {
        CourseEnrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found."));

        enrollmentRepository.delete(enrollment);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        enrollmentRepository.deleteById(id);
    }

    @Override
    public Optional<CourseEnrollment> findById(Long id) {
        return enrollmentRepository.findById(id);
    }

    @Override
    public List<CourseEnrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    @Override
    public boolean isEnrolled(Long userId, Long courseId) {
        return enrollmentRepository.existsByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public Optional<CourseEnrollment> findByUserIdAndCourseId(Long userId, Long courseId) {
        return enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public List<CourseEnrollment> findByUserId(Long userId) {
        return enrollmentRepository.findByUserId(userId);
    }

    @Override
    public List<CourseEnrollment> findByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    @Override
    public List<CourseEnrollment> searchEnrollments(Long courseId, Long userId, RoleInCourse role) {
        return enrollmentRepository.searchEnrollments(courseId, userId, role);
    }
}
