package realdeepmind.entity;

import jakarta.persistence.*;
import lombok.*;
import realdeepmind.entity.enums.RoleInCourse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "course_enrollments")
public class CourseEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleInCourse roleInCourse;

}
