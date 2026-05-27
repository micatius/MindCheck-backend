//package hr.tvz.mindcheck.mindcheckapp.repository;
//
//import hr.tvz.mindcheck.mindcheckapp.model.MoodEntry;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Primary
//@Repository
//public class JdbcMoodEntryRepository implements MoodEntryRepository {
//    private final JdbcTemplate jdbcTemplate;
//    private final SimpleJdbcInsert insertMoodEntry;
//
//    public JdbcMoodEntryRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.insertMoodEntry = new SimpleJdbcInsert(dataSource)
//                .withTableName("mood_entry")
//                .usingGeneratedKeyColumns("id");
//    }
//
//    @Override
//    public List<MoodEntry> findAll() {
//        String sql = """
//                SELECT id, mood, energy_level, stress_level, notes, logged_at
//                FROM mood_entry
//                ORDER BY id
//                """;
//        return jdbcTemplate.query(sql, this::mapRow);
//    }
//
//    @Override
//    public Optional<MoodEntry> findById(Long id) {
//        String sql = """
//                SELECT id, mood, energy_level, stress_level, notes, logged_at
//                FROM mood_entry
//                WHERE id = ?
//                """;
//        List<MoodEntry> result = jdbcTemplate.query(sql, this::mapRow, id);
//        return result.stream().findFirst();
//    }
//
//    @Override
//    public List<MoodEntry> findByMood(String val) {
//        String sql = """
//                SELECT id, mood, energy_level, stress_level, notes, logged_at
//                FROM mood_entry
//                WHERE LOWER(mood) = LOWER(?)
//                ORDER BY id
//                """;
//        return jdbcTemplate.query(sql, this::mapRow, val);
//    }
//
//    @Override
//    public boolean deleteMoodEntryById(Long id) {
//        String sql = "DELETE FROM mood_entry WHERE id = ?";
//        return jdbcTemplate.update(sql, id) > 0;
//    }
//
//    @Override
//    public MoodEntry save(MoodEntry moodEntry) {
//        String sql = """
//                INSERT INTO mood_entry (mood, energy_level, stress_level, notes, logged_at)
//                VALUES (?, ?, ?, ?, ?)
//                """;
//
//        jdbcTemplate.update(
//                sql,
//                moodEntry.getMood().name(),
//                moodEntry.getEnergyLevel(),
//                moodEntry.getStressLevel(),
//                moodEntry.getNotes(),
//                moodEntry.getLoggedAt() != null ? Timestamp.valueOf(moodEntry.getLoggedAt()) : Timestamp.valueOf(LocalDateTime.now())
//        );
//
//        Long generatedId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM mood_entry", Long.class);
//        moodEntry.setId(generatedId);
//        return moodEntry;
//    }
//
//    @Override
//    public MoodEntry update(MoodEntry moodEntry) {
//        String sql = """
//                UPDATE mood_entry
//                SET mood = ?,
//                    energy_level = ?,
//                    stress_level = ?,
//                    notes = ?,
//                    logged_at = ?
//                WHERE id = ?
//                """;
//
//        int updated = jdbcTemplate.update(
//                sql,
//                moodEntry.getMood().name(),
//                moodEntry.getEnergyLevel(),
//                moodEntry.getStressLevel(),
//                moodEntry.getNotes(),
//                moodEntry.getLoggedAt() != null ? Timestamp.valueOf(moodEntry.getLoggedAt()) : Timestamp.valueOf(LocalDateTime.now()),
//                moodEntry.getId()
//        );
//
//        return updated > 0 ? moodEntry : null;
//    }
//
//    @Override
//    public List<MoodEntry> findByEnergyLevelRange(Integer min, Integer max) {
//        String sql = """
//                SELECT id, mood, energy_level, stress_level, notes, logged_at
//                FROM mood_entry
//                WHERE energy_level BETWEEN ? AND ?
//                ORDER BY id
//                """;
//        return jdbcTemplate.query(sql, this::mapRow, min, max);
//    }
//
//    @Override
//    public List<MoodEntry> findByLoggedAtRange(LocalDate from, LocalDate to) {
//        String sql = """
//                SELECT id, mood, energy_level, stress_level, notes, logged_at
//                FROM mood_entry
//                WHERE logged_at >= ? AND logged_at < ?
//                ORDER BY id
//                """;
//
//        LocalDateTime fromDateTime = from.atStartOfDay();
//        LocalDateTime toDateTime = to.plusDays(1).atStartOfDay();
//
//        return jdbcTemplate.query(
//                sql,
//                this::mapRow,
//                Timestamp.valueOf(fromDateTime),
//                Timestamp.valueOf(toDateTime)
//        );
//    }
//
//    private MoodEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
//        return new MoodEntry(
//                rs.getLong("id"),
//                MoodEntry.Mood.valueOf(rs.getString("mood")),
//                rs.getInt("energy_level"),
//                rs.getInt("stress_level"),
//                rs.getString("notes"),
//                rs.getTimestamp("logged_at").toLocalDateTime()
//        );
//    }
//
//}
