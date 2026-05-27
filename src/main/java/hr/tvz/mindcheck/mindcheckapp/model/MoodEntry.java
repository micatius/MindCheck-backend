package hr.tvz.mindcheck.mindcheckapp.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="mood_entry")
public class MoodEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood", nullable = false, length = 20)
    private Mood mood;

    @Column(name = "energy_level")
    private Integer energyLevel;

    @Column(name = "stress_level")
    private Integer stressLevel;

    @Column(name = "notes", nullable = false, length = 1000)
    private String notes;

    @Column(name = "logged_at", nullable = false)
    private LocalDateTime loggedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private AppUser user;

    @OneToMany(mappedBy = "moodEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Reminder> reminders = new ArrayList<>();


    public enum Mood {
        GREAT, GOOD, OKAY, BAD, TERRIBLE
    }
}
