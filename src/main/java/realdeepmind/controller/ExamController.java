package realdeepmind.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import realdeepmind.dto.exam.ExamDto;
import realdeepmind.dto.exam.ExamResponseDto;
import realdeepmind.security.CustomUserDetails;
import realdeepmind.service.exam.ExamService;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') and @courseGuard.isTeacherOfCourse(#examDto.courseId)")
    public ResponseEntity<ExamResponseDto> createExam(@Valid @RequestBody ExamDto examDto) {
        return ResponseEntity.ok(examService.createExam(examDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') and @courseGuard.isTeacherOfExam(#id)")
    public ResponseEntity<ExamResponseDto> updateExam(
            @PathVariable Long id,
            @Valid @RequestBody ExamDto examDto
    ) {
        return ResponseEntity.ok(examService.updateExam(id, examDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') and @courseGuard.isTeacherOfExam(#id)")
    public ResponseEntity<String> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.ok("exam deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamResponseDto> getExamById(@PathVariable Long id) {
        return ResponseEntity.ok(examService.getExamById(id));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ExamResponseDto>> getExamsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(examService.getExamsByCourseId(courseId));
    }

    @GetMapping("/my-exams")
    public ResponseEntity<List<ExamResponseDto>> getMyExams() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long currentUserId = userDetails.getUser().getId();

        return ResponseEntity.ok(examService.getAllExamsByUserId(currentUserId));
    }
}