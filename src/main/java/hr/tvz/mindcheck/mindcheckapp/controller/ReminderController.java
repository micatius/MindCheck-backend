package hr.tvz.mindcheck.mindcheckapp.controller;

import hr.tvz.mindcheck.mindcheckapp.dto.ReminderDTO;
import hr.tvz.mindcheck.mindcheckapp.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderService reminderService;

    @GetMapping("/mood-entry/{moodEntryId}")
    public List<ReminderDTO> getByMoodEntryId(@PathVariable Long moodEntryId) {
        return reminderService.findByMoodEntryId(moodEntryId);
    }
}