package realdeepmind.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import realdeepmind.dto.course.CourseDto;
import realdeepmind.dto.course.CourseResponseDto;
import realdeepmind.dto.course_enrollment.EnrollmentRequestDto;
import realdeepmind.dto.course_enrollment.EnrollmentResponseDto;
import realdeepmind.entity.Course;
import realdeepmind.entity.CourseEnrollment;
import realdeepmind.mapper.CourseEnrollmentMapper;
import realdeepmind.mapper.CourseMapper;
import realdeepmind.service.course.CourseService;
import realdeepmind.service.course_enrollment.CourseEnrollmentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseEnrollmentService enrollmentService;
    private final CourseMapper courseMapper;
    private final CourseEnrollmentMapper enrollmentMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseDto courseDto) {
        Course course = courseMapper.toEntity(courseDto);
        Course savedCourse = courseService.createCourse(course);
        return ResponseEntity.ok(courseMapper.toDto(savedCourse));
    }

    @PostMapping("/enroll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnrollmentResponseDto> enrollUser(@RequestBody EnrollmentRequestDto requestDto) {
        CourseEnrollment enrollment = enrollmentService.enroll(
                requestDto.getUserId(),
                requestDto.getCourseId(),
                requestDto.getRole()
        );
        return ResponseEntity.ok(enrollmentMapper.toDto(enrollment));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        List<CourseResponseDto> dtos = courses.stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{courseId}/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<List<EnrollmentResponseDto>> getCourseUsers(@PathVariable Long courseId) {
        List<CourseEnrollment> enrollments = enrollmentService.findByCourseId(courseId);
        List<EnrollmentResponseDto> dtos = enrollments.stream()
                .map(enrollmentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        Course courseToUpdate = courseMapper.toEntity(courseDto);
        courseToUpdate.setId(id);
        Course updated = courseService.updateCourse(courseToUpdate);
        return ResponseEntity.ok(courseMapper.toDto(updated));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully.");
    }
}