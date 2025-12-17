package realdeepmind.dto.course;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CourseResponseDto {
    private Long id;
    private String title;
    private String courseCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer duration;
}
