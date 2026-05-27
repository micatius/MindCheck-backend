package hr.tvz.mindcheck.mindcheckapp.service;

import hr.tvz.mindcheck.mindcheckapp.command.MoodEntryCommand;
import hr.tvz.mindcheck.mindcheckapp.dto.MoodEntryDTO;
import hr.tvz.mindcheck.mindcheckapp.model.MoodEntry;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MoodEntryService {
    List<MoodEntryDTO> findAll();
    Optional<MoodEntryDTO> findById(Long id);
    List<MoodEntryDTO> findByMood(String val);
    boolean deleteMoodEntryById(@RequestParam Long id);
    MoodEntryDTO save(MoodEntryCommand moodEntry);
    MoodEntryDTO update(MoodEntry moodEntry);
    List<MoodEntryDTO> findByEnergyLevelRange(@RequestParam Integer min, @RequestParam Integer max);

    List<MoodEntryDTO> findByLoggedAtRange(LocalDate from, LocalDate to);
}
