package hr.tvz.mindcheck.mindcheckapp.repository;

import hr.tvz.mindcheck.mindcheckapp.model.MoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MoodEntryJpaRepository extends JpaRepository<MoodEntry, Long> {
    List<MoodEntry> findByMood(MoodEntry.Mood mood);

    List<MoodEntry> findByLoggedAtBetween(LocalDateTime loggedAtAfter, LocalDateTime loggedAtBefore);

    List<MoodEntry> findByEnergyLevelBetween(Integer min, Integer max);

    List<MoodEntry> findByUserId(Long userId);
}