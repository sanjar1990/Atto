package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.TransactionType;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private int id;
    private int card_number;
    private double amount;
    private Integer terminal_code;
    private TransactionType type;
    private LocalDateTime created_date;


}
