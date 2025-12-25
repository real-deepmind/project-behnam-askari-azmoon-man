package realdeepmind.service.course_enrollment;

import realdeepmind.entity.CourseEnrollment;
import realdeepmind.entity.enums.RoleInCourse;

import java.util.List;
import java.util.Optional;

public interface CourseEnrollmentService {


    CourseEnrollment enroll(Long userId, Long courseId, RoleInCourse role);

    CourseEnrollment assignGrade(Long userId, Long courseId, Double grade);

    void unenroll(Long userId, Long courseId);


    void deleteById(Long id);

    Optional<CourseEnrollment> findById(Long id);

    List<CourseEnrollment> findAll();

    boolean isEnrolled(Long userId, Long courseId);

    Optional<CourseEnrollment> findByUserIdAndCourseId(Long userId, Long courseId);

    List<CourseEnrollment> findByUserId(Long userId);

    List<CourseEnrollment> findByCourseId(Long courseId);

    List<CourseEnrollment> searchEnrollments(Long courseId, Long userId, RoleInCourse role);
    boolean checkEnrollmentRole(Long userId, Long courseId, RoleInCourse role);
}