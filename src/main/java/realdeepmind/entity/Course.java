package realdeepmind.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    @Builder.Default
    private List<CourseEnrollment> enrollments = new ArrayList<>();

}
