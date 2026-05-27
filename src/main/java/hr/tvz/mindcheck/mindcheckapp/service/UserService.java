package hr.tvz.mindcheck.mindcheckapp.service;

import hr.tvz.mindcheck.mindcheckapp.dto.UserDTO;
import hr.tvz.mindcheck.mindcheckapp.model.AppUser;
import hr.tvz.mindcheck.mindcheckapp.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserJpaRepository userRepo;

    public List<UserDTO> findByRegistrationDateBetween(LocalDate from, LocalDate to) {
        return userRepo.findByRegistrationDateBetween(from, to)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private UserDTO toDto(AppUser user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRegistrationDate(user.getRegistrationDate());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        return dto;
    }
}
