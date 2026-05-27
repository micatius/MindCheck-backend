package hr.tvz.mindcheck.mindcheckapp.dto;
import hr.tvz.mindcheck.mindcheckapp.model.MoodEntry.Mood;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MoodEntryDTO {
    private Long id;
    private Mood mood;
    private Integer energyLevel;
    private Integer stressLevel;
    private LocalDateTime loggedAt;
    private String notes;
    // private Integer activitiesCount;
}
