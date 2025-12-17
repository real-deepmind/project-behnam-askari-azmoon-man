package realdeepmind.mapper;

import org.springframework.stereotype.Component;
import realdeepmind.dto.course.CourseDto;
import realdeepmind.dto.course.CourseResponseDto;
import realdeepmind.entity.Course;

import java.time.temporal.ChronoUnit;

@Component
public class CourseMapper {

    public Course toEntity(CourseDto dto) {
        if (dto == null) {
            return null;
        }

        Course.CourseBuilder courseBuilder = Course.builder()
                .title(dto.getTitle())
                .courseCode(dto.getCourseCode())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate());

        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());
            courseBuilder.duration((int) days);
        }

        return courseBuilder.build();
    }

    public CourseResponseDto toDto(Course course) {
        if (course == null) {
            return null;
        }

        return CourseResponseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .courseCode(course.getCourseCode())
                .startDate(course.getStartDate())
                .endDate(course.getEndDate())
                .duration(course.getDuration())
                .build();
    }
}
