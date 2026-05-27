package hr.tvz.mindcheck.mindcheckapp.command;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}