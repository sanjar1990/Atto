package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.TerminalStatus;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerminalDto {
    private int code;
    private String address;
    private TerminalStatus status;
    private LocalDateTime created_date;
}
