package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.CardStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    private int card_number;
    private LocalDateTime created_date;
    private LocalDate exp_date;
    private Double balance;
    private CardStatus status;
    private String phone;

}


