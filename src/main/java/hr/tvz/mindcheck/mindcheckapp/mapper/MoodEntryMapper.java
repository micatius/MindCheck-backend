package hr.tvz.mindcheck.mindcheckapp.mapper;

import hr.tvz.mindcheck.mindcheckapp.command.MoodEntryCommand;
import hr.tvz.mindcheck.mindcheckapp.dto.MoodEntryDTO;
import hr.tvz.mindcheck.mindcheckapp.model.MoodEntry;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MoodEntryMapper {

    public MoodEntryDTO toDTO(MoodEntry moodEntry) {
        if (moodEntry == null) {
            return null;
        }
        MoodEntryDTO dto = new MoodEntryDTO();
        dto.setId(moodEntry.getId());
        dto.setMood(moodEntry.getMood());
        dto.setEnergyLevel(moodEntry.getEnergyLevel());
        dto.setStressLevel(moodEntry.getStressLevel());
        dto.setLoggedAt(moodEntry.getLoggedAt());
        dto.setNotes(moodEntry.getNotes());
        return dto;
    }

    public MoodEntry toEntity(MoodEntryCommand moodEntryCommand) {
        if (moodEntryCommand == null) {
            return null;
        }
        MoodEntry moodEntry = new MoodEntry();
        moodEntry.setId(null);
        moodEntry.setMood(moodEntryCommand.getMood());
        moodEntry.setEnergyLevel(moodEntryCommand.getEnergyLevel());
        moodEntry.setStressLevel(moodEntryCommand.getStressLevel());
        moodEntry.setNotes(moodEntryCommand.getNotes());
        moodEntry.setLoggedAt(moodEntryCommand.getLoggedAt() != null ? moodEntryCommand.getLoggedAt() : LocalDateTime.now());
        return moodEntry;
    }
}