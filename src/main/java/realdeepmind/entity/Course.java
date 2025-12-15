package realdeepmind.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "title cannot be empty")
    @Column(nullable = false)
    private String title;
    @NotBlank(message = "courseCode cannot be empty")
    @Column(nullable = false, unique = true)
    private String courseCode;
    @NotNull(message = "start date cannot be null")
    private LocalDate startDate;
    @NotNull(message = "end date cannot be null")
    private LocalDate endDate;
    private Integer duration;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;
    @ManyToMany
    @JoinTable(
            name = "course_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @Builder.Default
    private List<User> students = new ArrayList<>();

}
