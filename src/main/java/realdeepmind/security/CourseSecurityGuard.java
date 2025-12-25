package realdeepmind.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import realdeepmind.entity.Exam;
import realdeepmind.entity.enums.Role;
import realdeepmind.entity.enums.RoleInCourse;
import realdeepmind.repository.CourseEnrollmentRepository;
import realdeepmind.repository.ExamRepository;

@Component("courseGuard")
@RequiredArgsConstructor
public class CourseSecurityGuard {

    private final CourseEnrollmentRepository enrollmentRepository;
    private final ExamRepository examRepository;

    public boolean isTeacherOfCourse(Long courseId) {
        Long currentUserId = getCurrentUserId();

        return enrollmentRepository.existsByUserIdAndCourseIdAndRoleInCourse(
                currentUserId, courseId, RoleInCourse.TEACHER
        );
    }

    public boolean isTeacherOfExam(Long examId) {
        Long currentUserId = getCurrentUserId();

        Exam exam = examRepository.findById(examId).orElse(null);
        if (exam == null) return false;

        return enrollmentRepository.existsByUserIdAndCourseIdAndRoleInCourse(
                currentUserId, exam.getCourse().getId(), RoleInCourse.TEACHER
        );
    }

    public boolean isEnrolledInCourse(Long courseId) {
        Long currentUserId = getCurrentUserId();
        if (isAdmin()) {
            return true;
        }
        return enrollmentRepository.existsByUserIdAndCourseId(currentUserId, courseId);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getUser().getId();
        }
        return null;
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getUser().getRole() == Role.ADMIN;
        }
        return false;
    }

}
