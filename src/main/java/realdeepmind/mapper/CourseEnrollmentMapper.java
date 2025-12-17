package realdeepmind.mapper;

import org.springframework.stereotype.Component;
import realdeepmind.dto.course_enrollment.EnrollmentResponseDto;
import realdeepmind.entity.CourseEnrollment;

@Component
public class CourseEnrollmentMapper {

    public EnrollmentResponseDto toDto(CourseEnrollment enrollment) {
        if (enrollment == null) {
            return null;
        }

        return EnrollmentResponseDto.builder()
                .id(enrollment.getId())
                .userId(enrollment.getUser().getId())
                .userFullName(enrollment.getUser().getFirstName() + " " + enrollment.getUser().getLastName())
                .username(enrollment.getUser().getUsername())
                .courseId(enrollment.getCourse().getId())
                .courseTitle(enrollment.getCourse().getTitle())
                .role(enrollment.getRoleInCourse())
                .grade(enrollment.getGrade())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .build();
    }
}