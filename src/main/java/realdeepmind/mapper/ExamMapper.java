package realdeepmind.mapper;

import org.springframework.stereotype.Component;
import realdeepmind.dto.exam.ExamDto;
import realdeepmind.dto.exam.ExamResponseDto;
import realdeepmind.entity.Exam;

@Component
public class ExamMapper {

    public Exam toEntity(ExamDto dto) {
        if (dto == null) {
            return null;
        }

        return Exam.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .time(dto.getTime())
                .startDate(dto.getStartDate())
                .build();
    }

    public ExamResponseDto toDto(Exam exam) {
        if (exam == null) {
            return null;
        }

        return ExamResponseDto.builder()
                .id(exam.getId())
                .title(exam.getTitle())
                .description(exam.getDescription())
                .time(exam.getTime())
                .startDate(exam.getStartDate())
                .courseId(exam.getCourse().getId())
                .courseTitle(exam.getCourse().getTitle())
                .build();
    }
}
