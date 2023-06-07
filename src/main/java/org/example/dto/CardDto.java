package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.CardStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class CardDto {
    private int cardNumber;
    private LocalDateTime createdDate;
    private LocalDate expDate;
    private Double balance;
    private CardStatus cardStatus;
    private String userPhone;
    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public Double getBalance() {
        return balance;
    }


    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return "CardDto{" +
                "cardNumber=" + cardNumber +
                ", createdDate=" + createdDate +
                ", expDate=" + expDate +
                ", balance=" + balance +
                ", cardStatus=" + cardStatus +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}


