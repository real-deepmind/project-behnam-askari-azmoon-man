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
        createUserIfNotFound("admin", Role.ADMIN);

        createUserIfNotFound("reza", Role.TEACHER);
        createUserIfNotFound("sara", Role.TEACHER);
        createUserIfNotFound("mehdi", Role.TEACHER);
        createUserIfNotFound("neda", Role.TEACHER);

        createUserIfNotFound("ali", Role.STUDENT);
        createUserIfNotFound("mina", Role.STUDENT);
        createUserIfNotFound("hassan", Role.STUDENT);
        createUserIfNotFound("zahra", Role.STUDENT);
        createUserIfNotFound("kaveh", Role.STUDENT);
        createUserIfNotFound("maryam", Role.STUDENT);

        createCourseIfNotFound("Java Programming", "JAVA-101");
        createCourseIfNotFound("Advanced Python", "PY-202");
        createCourseIfNotFound("Data Structures", "CS-303");
        createCourseIfNotFound("Database Systems", "DB-404");

        enrollUserInCourse("reza", "JAVA-101", RoleInCourse.TEACHER);
        enrollUserInCourse("ali", "JAVA-101", RoleInCourse.STUDENT);
        enrollUserInCourse("mina", "JAVA-101", RoleInCourse.STUDENT);

        enrollUserInCourse("sara", "PY-202", RoleInCourse.TEACHER);
        enrollUserInCourse("hassan", "PY-202", RoleInCourse.STUDENT);
        enrollUserInCourse("zahra", "PY-202", RoleInCourse.STUDENT);
        enrollUserInCourse("ali", "PY-202", RoleInCourse.STUDENT);

        enrollUserInCourse("mehdi", "CS-303", RoleInCourse.TEACHER);
        enrollUserInCourse("kaveh", "CS-303", RoleInCourse.STUDENT);
        enrollUserInCourse("maryam", "CS-303", RoleInCourse.STUDENT);

        enrollUserInCourse("neda", "DB-404", RoleInCourse.TEACHER);
        enrollUserInCourse("mina", "DB-404", RoleInCourse.STUDENT);
        enrollUserInCourse("hassan", "DB-404", RoleInCourse.STUDENT);
    }

    private void createUserIfNotFound(String username, Role role) {
        if (!userRepository.existsByUsername(username)) {
            User user = User.builder()
                    .firstName(username)
                    .lastName("Test")
                    .username(username)
                    .password(passwordEncoder.encode("123456"))
                    .role(role)
                    .userStatus(UserStatus.APPROVED)
                    .build();
            userRepository.save(user);
            System.out.println(" User created: " + username);
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
                System.out.println(" Enrolled: " + username + " in " + courseCode);
            }
        }
    }
}