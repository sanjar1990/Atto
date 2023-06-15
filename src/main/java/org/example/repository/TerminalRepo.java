package org.example.repository;

import org.example.dto.TerminalDto;
import org.example.enums.TerminalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
@Repository
public class TerminalRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public TerminalDto checkTerminalByNum(int terminalNum) {
        String sql="select * from terminal where code=?";
        List<TerminalDto> terminalDtoList=jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TerminalDto.class), terminalNum);
        return terminalDtoList.isEmpty()?null:terminalDtoList.get(0);
    }

    public boolean createTerminal(TerminalDto terminalDto) {
        String sql="insert into terminal(code,address,status,created_date) values(?,?,?,?)";
        int i=jdbcTemplate.update(sql,terminalDto.getCode(),terminalDto.getAddress(),terminalDto.getStatus().name(),
                terminalDto.getCreated_date());
        return i==1;
    }

    public List<TerminalDto> getAllTerminal() {
        String sql="select * from terminal";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TerminalDto.class));   }


    public boolean updateTerminal(int oldCode, int newCode, String newAddress) {
        String sql="update terminal set code=?, address=? where code=?";
        int i=jdbcTemplate.update(sql, newCode, newAddress, oldCode);
        return i==1;
    }

    public boolean updateTerminalStatus(int terminalCode, String newStatus) {
        String sql="update terminal set status=? where code=?";
        int i=jdbcTemplate.update(sql,newStatus,terminalCode);
        return i==1;
    }

}
