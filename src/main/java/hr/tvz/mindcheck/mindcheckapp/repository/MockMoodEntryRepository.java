package hr.tvz.mindcheck.mindcheckapp.repository;

import hr.tvz.mindcheck.mindcheckapp.model.MoodEntry;
import hr.tvz.mindcheck.mindcheckapp.model.MoodEntry.Mood;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class MockMoodEntryRepository implements MoodEntryRepository {
    private final List<MoodEntry> moodEntries = new ArrayList<>();
    private final Random random = new Random();

    MockMoodEntryRepository() {
//        moodEntries.addAll(List.of(
//                new MoodEntry(1L, Mood.GOOD, 5, 4, "Prosječan dan", LocalDateTime.now().minusDays(1)),
//                new MoodEntry(2L, Mood.TERRIBLE, 3, 8, "Posjet obitelji", LocalDateTime.now().minusDays(2)),
//                new MoodEntry(3L, Mood.GREAT, 8, 1, "Opuštanje na vikend", LocalDateTime.now().minusDays(3)),
//                new MoodEntry(4L, Mood.OKAY, 6, 5, "Radni dan bez stresa", LocalDateTime.now().minusDays(4)),
//                new MoodEntry(5L, Mood.BAD, 4, 7, "Loš sastanak na poslu", LocalDateTime.now().minusDays(5)),
//                new MoodEntry(6L, Mood.GOOD, 7, 3, "Trening nakon posla", LocalDateTime.now().minusDays(6)),
//                new MoodEntry(7L, Mood.GREAT, 9, 2, "Izlet s prijateljima", LocalDateTime.now().minusDays(7)),
//                new MoodEntry(8L, Mood.TERRIBLE, 2, 9, "Bolest i loš san", LocalDateTime.now().minusDays(8)),
//                new MoodEntry(9L, Mood.OKAY, 5, 5, "Ništa posebno", LocalDateTime.now().minusDays(9)),
//                new MoodEntry(10L, Mood.GOOD, 6, 4, "Kava s kolegom", LocalDateTime.now().minusDays(10)),
//                new MoodEntry(11L, Mood.BAD, 3, 8, "Prometna gužva", LocalDateTime.now().minusDays(11)),
//                new MoodEntry(12L, Mood.GREAT, 10, 1, "Odličan dan na poslu", LocalDateTime.now().minusDays(12)),
//                new MoodEntry(13L, Mood.OKAY, 5, 6, "Umor tijekom dana", LocalDateTime.now().minusDays(13)),
//                new MoodEntry(14L, Mood.GOOD, 7, 3, "Film navečer", LocalDateTime.now().minusDays(14)),
//                new MoodEntry(15L, Mood.TERRIBLE, 1, 10, "Jako loš dan", LocalDateTime.now().minusDays(15)),
//                new MoodEntry(16L, Mood.BAD, 4, 7, "Stres na poslu", LocalDateTime.now().minusDays(16)),
//                new MoodEntry(17L, Mood.OKAY, 6, 5, "Standardan dan", LocalDateTime.now().minusDays(17)),
//                new MoodEntry(18L, Mood.GREAT, 9, 2, "Dobar izlazak", LocalDateTime.now().minusDays(18)),
//                new MoodEntry(19L, Mood.GOOD, 8, 3, "Produktivan dan", LocalDateTime.now().minusDays(19)),
//                new MoodEntry(20L, Mood.BAD, 3, 7, "Manjak sna", LocalDateTime.now().minusDays(20)),
//                new MoodEntry(21L, Mood.OKAY, 5, 6, "Lagani radni tempo", LocalDateTime.now().minusDays(21)),
//                new MoodEntry(22L, Mood.GREAT, 10, 1, "Slobodan dan", LocalDateTime.now().minusDays(22)),
//                new MoodEntry(23L, Mood.TERRIBLE, 2, 9, "Loše vijesti", LocalDateTime.now().minusDays(23))
//                ));
    }
    @Override
    public List<MoodEntry> findAll() {
        return moodEntries;
    }

    @Override
    public Optional<MoodEntry> findById(Long id) {
        return moodEntries.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    @Override
    public List<MoodEntry> findByMood(String val) {
        return moodEntries.stream().filter(e -> e.getMood().toString().equalsIgnoreCase(val)).toList();
    }

    @Override
    public boolean deleteMoodEntryById(Long id) {
        return moodEntries.removeIf(e -> e.getId().equals(id));
    }

    @Override
    public MoodEntry save(MoodEntry moodEntry) {
        moodEntry.setId(random.nextLong(0L, 1000000000L));
        moodEntries.add(moodEntry);
        return moodEntry;
    }

    @Override
    public MoodEntry update(MoodEntry moodEntry) {
        moodEntries.removeIf(e -> e.getId().equals(moodEntry.getId()));
        moodEntries.add(moodEntry);
        return moodEntry;
    }

    @Override
    public List<MoodEntry> findByEnergyLevelRange(Integer min, Integer max) {
        return moodEntries.stream()
                .filter(m -> m.getEnergyLevel() >= min && m.getEnergyLevel() <= max).toList();
    }

    @Override
    public List<MoodEntry> findByLoggedAtRange(LocalDate from, LocalDate to) {
        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.plusDays(1).atStartOfDay();

        return moodEntries.stream().filter(e ->
                (!e.getLoggedAt().isBefore(fromDateTime) && e.getLoggedAt().isBefore(toDateTime)
                )).toList();
    }
}
