package hr.tvz.mindcheck.mindcheckapp.service;

import hr.tvz.mindcheck.mindcheckapp.command.MoodEntryCommand;
import hr.tvz.mindcheck.mindcheckapp.dto.MoodEntryDTO;
import hr.tvz.mindcheck.mindcheckapp.mapper.MoodEntryMapper;
import hr.tvz.mindcheck.mindcheckapp.model.AppUser;
import hr.tvz.mindcheck.mindcheckapp.model.MoodEntry;
import hr.tvz.mindcheck.mindcheckapp.repository.MoodEntryJpaRepository;
import hr.tvz.mindcheck.mindcheckapp.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MoodEntryServiceImpl implements MoodEntryService {
    private final MoodEntryJpaRepository repo;
    private final MoodEntryMapper mapper;
    private final UserJpaRepository userRepository;

    @Override
    public List<MoodEntryDTO> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return List.of();
        }
        if (hasRole(authentication, "ROLE_ADMIN")) {
            return repo.findAll().stream().map(mapper::toDTO).toList();
        }
        AppUser currentUser = getCurrentUser();
        if (currentUser == null) {
            return List.of();
        }
        return repo.findByUserId(currentUser.getId()).stream().map(mapper::toDTO).toList();
    }

    @Override
    public Optional<MoodEntryDTO> findById(Long id) {
        if (!isAuthenticated()) {
            return Optional.empty();
        }
        return repo.findById(id)
                .filter(entry -> isAdmin() || isOwner(entry))
                .map(mapper::toDTO);
    }

    @Override
    public List<MoodEntryDTO> findByMood(String val) {
        MoodEntry.Mood mood = MoodEntry.Mood.valueOf(val.toUpperCase());
        return repo.findByMood(mood).stream().map(mapper::toDTO).toList();
    }

    @Override
    public boolean deleteMoodEntryById(Long id) {
        if (!isAuthenticated()) {
            return false;
        }
        if (isGuest()) {
            return false;
        }
        return repo.findById(id).map(existing -> {
            if (isAdmin() || isOwner(existing)) {
                repo.deleteById(id);
                return true;
            }
            return false;
        }).orElse(false);
    }

    @Override
    public MoodEntryDTO save(MoodEntryCommand moodEntry) {
        if (!isAuthenticated()) {
            return null;
        }
        MoodEntry moodEntryEntity = mapper.toEntity(moodEntry);
        AppUser currentUser = getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        moodEntryEntity.setUser(currentUser);
        return mapper.toDTO(repo.save(moodEntryEntity));
    }

    @Override
    public MoodEntryDTO update(MoodEntry moodEntry) {
        if (moodEntry == null || moodEntry.getId() == null || !isAuthenticated()) {
            return null;
        }
        if (isGuest()) {
            return null;
        }
        return repo.findById(moodEntry.getId()).map(existing -> {
            if (!isAdmin() && !isOwner(existing)) {
                return null;
            }

            existing.setMood(moodEntry.getMood());
            existing.setEnergyLevel(moodEntry.getEnergyLevel());
            existing.setStressLevel(moodEntry.getStressLevel());
            existing.setNotes(moodEntry.getNotes());
            existing.setLoggedAt(moodEntry.getLoggedAt());
            return mapper.toDTO(repo.save(existing));
        }).orElse(null);
    }

    @Override
    public List<MoodEntryDTO> findByEnergyLevelRange(Integer min, Integer max) {
        return repo.findByEnergyLevelBetween(min, max).stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<MoodEntryDTO> findByLoggedAtRange(LocalDate from, LocalDate to) {
        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.plusDays(1).atStartOfDay();
        return repo.findByLoggedAtBetween(fromDateTime, toDateTime).stream().map(mapper::toDTO).toList();
    }

    private AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal)) {
            return null;
        }
        return userRepository.findByUsername(authentication.getName()).orElse(null);
    }

    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role::equals);
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal());
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && hasRole(authentication, "ROLE_ADMIN");
    }

    private boolean isGuest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && hasRole(authentication, "ROLE_GUEST");
    }

    private boolean isOwner(MoodEntry entry) {
        AppUser currentUser = getCurrentUser();
        return currentUser != null && entry.getUser() != null && currentUser.getId().equals(entry.getUser().getId());
    }
}
