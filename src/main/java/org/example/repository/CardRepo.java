package org.example.repository;

import org.example.dto.CardDto;
import org.example.enums.CardStatus;
import org.example.enums.ProfileRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class CardRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void addCommission(CardDto cardDto) {
        String sql="update card set balance=? where phone=?";
        jdbcTemplate.update(sql,cardDto.getBalance(),cardDto.getPhone());
    }


    public  CardDto checkCardByNum(int cardNumber){
        String sql="select * from card where card_number=?";
        List<CardDto> cardDtoList=jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CardDto.class), cardNumber);
        return cardDtoList.isEmpty()? null:cardDtoList.get(0);
    }

    public boolean add_card(CardDto cardDto){
        String sql="update card set status=?, phone=? where card_number=?";
        int i=jdbcTemplate.update(sql,cardDto.getStatus().name(),cardDto.getPhone(), cardDto.getCard_number());
        return i==1;
    }

public List<CardDto> getCardList(String phone) {
    String sql="select * from card where phone=?";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CardDto.class),phone);
}

    public void update_card(CardDto cardDto) {
        String sql="update card set status=? where card_number=? ";
        jdbcTemplate.update(sql,cardDto.getStatus().name(), cardDto.getCard_number());
    }
public CardDto getAdminCard() {
    String sql="select * from card as c " +
            "inner join profile as p " +
            "on c.phone=p.phone " +
            "where p.role=?";
    List<CardDto> cardDtoList=jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CardDto.class),ProfileRole.ADMIN.name());
    return cardDtoList.isEmpty()? null: cardDtoList.get(0);
}
    public boolean refillCard(CardDto cardDto) {
        String sql="update card set balance=? where phone=?";
        int i=jdbcTemplate.update(sql,cardDto.getBalance(),cardDto.getPhone());
        return i==1;
    }

    public boolean create_card(CardDto cardDto){
        String sql="insert into card(card_number, created_date, exp_date, balance, status, phone) values(?,?,?,?,?,?)";
        int i=jdbcTemplate.update(sql,cardDto.getCard_number(),cardDto.getCreated_date(),cardDto.getExp_date(),
                cardDto.getBalance(),cardDto.getStatus().name(),cardDto.getPhone());
        return i==1;
    }
    public List<CardDto> getCardList() {
        String sql="select * from card ";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(CardDto.class));
    }
    public void updateCardExpDate(CardDto cardDto) {
        String sql="update card set exp_date=? where card_number=? ";
        jdbcTemplate.update(sql,cardDto.getExp_date(),cardDto.getCard_number());
    }
    public CardDto getCardBalance(String phone) {
        String sql="select * from card where phone=?";
        List<CardDto>cardDtoList=jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CardDto.class),phone);
        return cardDtoList.isEmpty()?null:cardDtoList.get(0);
    }



}
