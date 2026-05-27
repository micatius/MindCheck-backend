package hr.tvz.mindcheck.mindcheckapp.controller;

import hr.tvz.mindcheck.mindcheckapp.dto.MoodEntryDTO;
import hr.tvz.mindcheck.mindcheckapp.dto.ReminderDTO;
import hr.tvz.mindcheck.mindcheckapp.dto.UserDTO;
import hr.tvz.mindcheck.mindcheckapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/date")
    public ResponseEntity<List<UserDTO>> findByRegistrationDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to) {

        List<UserDTO> result = userService.findByRegistrationDateBetween(from, to);

        return result.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(result);
    }
}