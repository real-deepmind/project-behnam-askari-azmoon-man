package realdeepmind.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import realdeepmind.entity.Course;
import realdeepmind.entity.CourseEnrollment;
import realdeepmind.entity.User;
import realdeepmind.entity.enums.Role;
import realdeepmind.entity.enums.RoleInCourse;
import realdeepmind.entity.enums.UserStatus;
import realdeepmind.repository.CourseEnrollmentRepository;
import realdeepmind.repository.CourseRepository;
import realdeepmind.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseEnrollmentRepository enrollmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        createUserIfNotFound("admin", "admin123", Role.ADMIN);
        createUserIfNotFound("teacher1", "1234", Role.TEACHER);
        createUserIfNotFound("teacher2", "1234", Role.TEACHER);
        createUserIfNotFound("student1", "1234", Role.STUDENT);
        createUserIfNotFound("student2", "1234", Role.STUDENT);

        createCourseIfNotFound("Java Programming", "JAVA-101");
        createCourseIfNotFound("Advanced Python", "PY-202");


        enrollUserInCourse("teacher1", "JAVA-101", RoleInCourse.TEACHER);
        enrollUserInCourse("student1", "JAVA-101", RoleInCourse.STUDENT);
        enrollUserInCourse("student2", "JAVA-101", RoleInCourse.STUDENT);

        enrollUserInCourse("teacher2", "PY-202", RoleInCourse.TEACHER);
        enrollUserInCourse("student1", "PY-202", RoleInCourse.STUDENT);
    }

    private void createUserIfNotFound(String username, String rawPassword, Role role) {
        if (!userRepository.existsByUsername(username)) {
            User user = User.builder()
                    .firstName(role.name().substring(0, 1) + role.name().toLowerCase().substring(1))
                    .lastName("Test")
                    .username(username)
                    .password(passwordEncoder.encode(rawPassword))
                    .role(role)
                    .userStatus(UserStatus.APPROVED)
                    .build();
            userRepository.save(user);
            System.out.println("User created: " + username);
        }
    }

    private void createCourseIfNotFound(String title, String courseCode) {
        if (!courseRepository.existsByCourseCode(courseCode)) {
            Course course = Course.builder()
                    .title(title)
                    .courseCode(courseCode)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusMonths(3))
                    .duration(90)
                    .build();
            courseRepository.save(course);
            System.out.println(" Course created: " + title);
        }
    }

    private void enrollUserInCourse(String username, String courseCode, RoleInCourse roleInCourse) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        Optional<Course> courseOpt = courseRepository.findByCourseCode(courseCode);

        if (userOpt.isPresent() && courseOpt.isPresent()) {
            User user = userOpt.get();
            Course course = courseOpt.get();

            if (!enrollmentRepository.existsByUserIdAndCourseId(user.getId(), course.getId())) {
                CourseEnrollment enrollment = CourseEnrollment.builder()
                        .user(user)
                        .course(course)
                        .roleInCourse(roleInCourse)
                        .build();
                enrollmentRepository.save(enrollment);
                System.out.println("Enrolled: " + username + " in " + courseCode + " as " + roleInCourse);
            }
        }
    }
}