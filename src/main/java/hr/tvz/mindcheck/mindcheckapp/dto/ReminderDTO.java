package hr.tvz.mindcheck.mindcheckapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReminderDTO {
    private Long id;
    private String title;
    private String message;
    private String cronExpression;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private Long moodEntryId;
}