package realdeepmind.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "this field can not be emprty")
    private String title;
    @Column(length = 300)
    private String description;
    @Column(nullable = false)
    @NotNull(message = "this field can not be empty!")
    @Min(1)
    private Integer time;
    @NotNull(message = "Start date and time is required")
    private LocalDateTime startDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}
