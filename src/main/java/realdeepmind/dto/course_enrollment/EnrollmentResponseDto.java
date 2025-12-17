package realdeepmind.dto.course_enrollment;

import lombok.Builder;
import lombok.Data;
import realdeepmind.entity.enums.RoleInCourse;

import java.time.LocalDateTime;

@Data
@Builder
public class EnrollmentResponseDto {
    private Long id;
    private Long userId;
    private String userFullName;
    private String username;

    private Long courseId;
    private String courseTitle;

    private RoleInCourse role;
    private Double grade;
    private LocalDateTime enrollmentDate;
}