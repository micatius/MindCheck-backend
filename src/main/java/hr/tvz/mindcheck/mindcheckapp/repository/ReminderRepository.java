package hr.tvz.mindcheck.mindcheckapp.repository;

import hr.tvz.mindcheck.mindcheckapp.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByMoodEntryId(Long moodEntryId);
}