package hr.tvz.mindcheck.mindcheckapp.repository;

import hr.tvz.mindcheck.mindcheckapp.model.AppUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<AppUser, Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<AppUser> findByUsername(String username);
    List<AppUser> findByRegistrationDateBetween(LocalDate from, LocalDate to);


}
