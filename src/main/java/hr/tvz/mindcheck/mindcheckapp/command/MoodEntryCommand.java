package hr.tvz.mindcheck.mindcheckapp.command;


import hr.tvz.mindcheck.mindcheckapp.model.MoodEntry.Mood;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MoodEntryCommand {
    @NotNull
    private Mood mood;
    @Min(1) @Max(10)
    private Integer energyLevel;
    @Min(1) @Max(10)
    private Integer stressLevel;
    @Size(max=2000)
    @NotBlank
    private String notes;
    private LocalDateTime loggedAt;

}
