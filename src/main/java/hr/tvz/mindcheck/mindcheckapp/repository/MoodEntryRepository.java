package hr.tvz.mindcheck.mindcheckapp.repository;

import hr.tvz.mindcheck.mindcheckapp.model.MoodEntry;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MoodEntryRepository {
    List<MoodEntry> findAll();
    Optional<MoodEntry> findById(Long id);
    List<MoodEntry> findByMood(String val);
    boolean deleteMoodEntryById(Long id);
    MoodEntry save(MoodEntry moodEntry);
    MoodEntry update(MoodEntry moodEntry);
    List<MoodEntry> findByEnergyLevelRange(Integer min, Integer max);
    List<MoodEntry> findByLoggedAtRange(LocalDate from, LocalDate to);
}
