package hr.tvz.mindcheck.mindcheckapp.controller;

import hr.tvz.mindcheck.mindcheckapp.command.MoodEntryCommand;
import hr.tvz.mindcheck.mindcheckapp.dto.MoodEntryDTO;
import hr.tvz.mindcheck.mindcheckapp.model.MoodEntry;
import hr.tvz.mindcheck.mindcheckapp.service.MoodEntryService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/moodEntries")
public class MoodEntryController {
    private final MoodEntryService moodEntryService;

    public MoodEntryController(MoodEntryService moodEntryService) {
        this.moodEntryService = moodEntryService;
    }

    @GetMapping
    public List<MoodEntryDTO> getMoodEntries() {
        return moodEntryService.findAll();
    }

    @GetMapping("/date")
    public ResponseEntity<List<MoodEntryDTO>> getByLoggedAtRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to) {

        List<MoodEntryDTO> result = moodEntryService.findByLoggedAtRange(from, to);

        return result.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MoodEntryDTO> getMoodEntryById(@PathVariable Long id) {
        return moodEntryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mood/{mood}")
    public ResponseEntity<List<MoodEntryDTO>> getMoodEntriesByMood(@PathVariable String mood) {
        List<MoodEntryDTO> result = moodEntryService.findByMood(mood);
        return result.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MoodEntryDTO> deleteMoodEntryById(@PathVariable Long id) {
        return moodEntryService.deleteMoodEntryById(id) ?
                ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<MoodEntryDTO> saveMoodEntry(@Valid @RequestBody MoodEntryCommand moodEntry) {
        MoodEntryDTO savedMoodEntry = moodEntryService.save(moodEntry);
        if (savedMoodEntry != null) {
            return new ResponseEntity<>(savedMoodEntry, HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.CONFLICT);

    }

    @PutMapping
    public ResponseEntity<MoodEntryDTO> updateMoodEntry(@Valid @RequestBody MoodEntry moodEntry) {
        MoodEntryDTO updatedMoodEntry = moodEntryService.update(moodEntry);
        if (updatedMoodEntry != null) {
            return new ResponseEntity<>(updatedMoodEntry, HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.CONFLICT);

    }

    @GetMapping("/energy")
    public List<MoodEntryDTO> getMoodEntriesByEnergyLevelRange(@RequestParam Integer min, @RequestParam Integer max) {
        return moodEntryService.findByEnergyLevelRange(min, max);
    }




}
