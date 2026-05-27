package hr.tvz.mindcheck.mindcheckapp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate registrationDate;
    private String email;
    private String phone;
}
