package realdeepmind.dto.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamDto {

    @NotBlank(message = "can not be blank")
    private String title;

    private String description;

    @NotNull(message = "set time is Necessary")
    @Min(value = 1, message = "minimum time is 1 minute")
    private Integer time;

    @NotNull(message = "start time is Necessary")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;

    @NotNull(message = "course id is necessary for exam!")
    private Long courseId;
}