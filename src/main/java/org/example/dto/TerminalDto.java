package org.example.dto;

import org.example.enums.TerminalStatus;

import java.time.LocalDateTime;

public class TerminalDto {
    private int terminalCode;
    private String address;
    private TerminalStatus terminalStatus;
    private LocalDateTime created_date;

    public int getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(int terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TerminalStatus getTerminalStatus() {
        return terminalStatus;
    }

    public void setTerminalStatus(TerminalStatus terminalStatus) {
        this.terminalStatus = terminalStatus;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    @Override
    public String toString() {
        return "TerminalDto{" +
                "terminalCode=" + terminalCode +
                ", address='" + address + '\'' +
                ", terminalStatus=" + terminalStatus +
                ", created_date=" + created_date +
                '}';
    }
}
