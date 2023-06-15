package org.example.repository;

import org.example.dto.CardDto;
import org.example.dto.TerminalDto;
import org.example.dto.TransactionDto;
import org.example.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
@Repository
public class TransactionRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void addTransactionInfo(TransactionDto transactionDto) {
        String sql="insert into transaction(card_number,amount, terminal_code, type, created_date) values(?,?,?,?,?)";
        jdbcTemplate.update(sql,transactionDto.getCard_number(),transactionDto.getAmount(),transactionDto.getTerminal_code(),
                transactionDto.getType().name(),transactionDto.getCreated_date());

    }
    public List<TransactionDto> getUserTransactionHistory(String phone) {
        String sql="select * from transaction as t inner join card as c " +
                "on t.card_number=c.card_number where c.phone=? order by id desc";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(TransactionDto.class),phone);
    }

    public List<TransactionDto> getAllTransactionHistory() {
        String sql="select * from transaction order by id desc";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionDto.class));

    }
    public List<TransactionDto> getTodayTransaction(LocalDate today) {
        String sql="select * from transaction where created_date >? order by id desc ";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(TransactionDto.class),today);
    }

    public List<TransactionDto> getTransactionBetween(LocalDate fromDate, LocalDate toDate) {
        String sql="select * from transaction where created_date >? and created_date<? order by id desc ";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(TransactionDto.class), fromDate, toDate);
    }
    public List<TransactionDto> getTransactionByTerminal(TerminalDto terminalDto) {
        String sql="select * from transaction where terminal_code=?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(TransactionDto.class), terminalDto.getCode());
    }

    public List<TransactionDto> getTransactionByCard(CardDto cardDto) {
        String sql="select * from transaction where card_number=? " +
                " order by id desc";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionDto.class), cardDto.getCard_number());
    }

}

