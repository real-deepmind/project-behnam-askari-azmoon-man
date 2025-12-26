package realdeepmind.service.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realdeepmind.dto.exam.ExamDto;
import realdeepmind.dto.exam.ExamResponseDto;
import realdeepmind.entity.Course;
import realdeepmind.entity.Exam;
import realdeepmind.exception.BadRequestException;
import realdeepmind.exception.ResourceNotFoundException;
import realdeepmind.mapper.ExamMapper;
import realdeepmind.repository.CourseRepository;
import realdeepmind.repository.ExamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;
    private final ExamMapper examMapper;

    @Override
    public ExamResponseDto createExam(ExamDto examDto) {
        if (examRepository.existsByCourseId(examDto.getCourseId())) {
            throw new BadRequestException("teacher only can create one exam for this course");
        }
        Exam exam = examMapper.toEntity(examDto);

        Course course = courseRepository.findById(examDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("course not find" + examDto.getCourseId()));

        exam.setCourse(course);

        Exam savedExam = examRepository.save(exam);

        return examMapper.toDto(savedExam);
    }


    @Override
    public ExamResponseDto updateExam(Long examId, ExamDto examDto) {
        Exam existingExam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("exam not find" + examId));

        existingExam.setTitle(examDto.getTitle());
        existingExam.setDescription(examDto.getDescription());
        existingExam.setTime(examDto.getTime());
        existingExam.setStartDate(examDto.getStartDate());

        if (!existingExam.getCourse().getId().equals(examDto.getCourseId())) {
            Course newCourse = courseRepository.findById(examDto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("new course not find"));
            existingExam.setCourse(newCourse);
        }

        Exam updatedExam = examRepository.save(existingExam);
        return examMapper.toDto(updatedExam);
    }


    @Override
    public void deleteExam(Long examId) {
        if (!examRepository.existsById(examId)) {
            throw new ResourceNotFoundException("exam not found " + examId);
        }
        examRepository.deleteById(examId);
    }


    @Override
    @Transactional(readOnly = true)
    public ExamResponseDto getExamById(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("exam not found"));
        return examMapper.toDto(exam);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ExamResponseDto> getExamsByCourseId(Long courseId) {
        List<Exam> exams = examRepository.findByCourseIdOrderByStartDateAsc(courseId);

        return exams.stream()
                .map(examMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<ExamResponseDto> getAllExamsByUserId(Long userId) {
        List<Exam> exams = examRepository.findAllExamsByUserId(userId);

        return exams.stream()
                .map(examMapper::toDto)
                .collect(Collectors.toList());
    }
}