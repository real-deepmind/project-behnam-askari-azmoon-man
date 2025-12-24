package realdeepmind.service.exam;

import realdeepmind.dto.exam.ExamDto;
import realdeepmind.dto.exam.ExamResponseDto;

import java.util.List;

public interface ExamService {
    ExamResponseDto createExam(ExamDto examDto);

    ExamResponseDto updateExam(Long examId, ExamDto examDto);

    void deleteExam(Long examId);

    ExamResponseDto getExamById(Long examId);

    List<ExamResponseDto> getExamsByCourseId(Long courseId);

    List<ExamResponseDto> getAllExamsByUserId(Long userId);
}