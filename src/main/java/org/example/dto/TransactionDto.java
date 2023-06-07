package org.example.dto;

import org.example.enums.TransactionType;

import java.time.LocalDateTime;

public class TransactionDto {
    private int id;
    private int cardNumber;
    private double amount;
    private int terminalCode;
    private TransactionType transactionType;
    private LocalDateTime createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(int terminalCode) {
        this.terminalCode = terminalCode;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", cardNumber=" + cardNumber +
                ", amount=" + amount +
                ", terminalCode=" + terminalCode +
                ", transactionType=" + transactionType +
                ", createdDate=" + createdDate +
                '}';
    }
}
