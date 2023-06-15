package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private int id;
    private String  name;
    private String surname;
    private String phone;
    private String password;
    private String login;
    private LocalDateTime created_date;
    private ProfileStatus status;
    private ProfileRole role;
}
