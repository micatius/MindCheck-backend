package hr.tvz.mindcheck.mindcheckapp.service;

import hr.tvz.mindcheck.mindcheckapp.dto.ReminderDTO;
import hr.tvz.mindcheck.mindcheckapp.model.Reminder;
import hr.tvz.mindcheck.mindcheckapp.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;

    public List<ReminderDTO> findByMoodEntryId(Long moodEntryId) {
        return reminderRepository.findByMoodEntryId(moodEntryId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private ReminderDTO toDto(Reminder reminder) {
        ReminderDTO dto = new ReminderDTO();
        dto.setId(reminder.getId());
        dto.setTitle(reminder.getTitle());
        dto.setMessage(reminder.getMessage());
        dto.setCronExpression(reminder.getCronExpression());
        dto.setIsActive(reminder.getIsActive());
        dto.setCreatedAt(reminder.getCreatedAt());
        dto.setMoodEntryId(reminder.getMoodEntry().getId());
        return dto;
    }
}