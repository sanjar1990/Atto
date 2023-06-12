package org.example.repository;

import org.example.dto.CardDto;
import org.example.enums.CardStatus;
import org.example.util.DBConnection;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
@Repository
public class UserRepo {
    public CardDto checkCardByNum(int cardNum){
        Connection connection= DBConnection.getConnection();
        String sql="select * from card where card_number=?";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setInt(1,cardNum);
            ResultSet rs=pr.executeQuery();
            if(rs.next()){
                CardDto cardDto= new CardDto();
                cardDto.setCardNumber(rs.getInt("card_number"));
                cardDto.setBalance(rs.getDouble("balance"));
                cardDto.setCreatedDate(rs.getTimestamp("crated_date").toLocalDateTime());
                cardDto.setExpDate(rs.getDate("exp_date").toLocalDate());
                cardDto.setCardStatus(CardStatus.valueOf(rs.getString("status")));
                cardDto.setUserPhone(rs.getString("phone"));
                return cardDto;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public boolean add_card(CardDto cardDto){
        Connection connection= DBConnection.getConnection();
        String sql="update card set status=?, phone=? where card_number=?";
        try {
            PreparedStatement pr= connection.prepareStatement(sql);
            pr.setString(1,cardDto.getCardStatus().toString());
            pr.setString(2, cardDto.getUserPhone());
            pr.setInt(3,cardDto.getCardNumber());
            return  pr.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public List<CardDto> getCardList(String phone) {
        Connection connection= DBConnection.getConnection();
        List<CardDto> cardDtoList=new LinkedList<>();
        String sql="select * from card where phone=?";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setString(1,phone);
            ResultSet rs=pr.executeQuery();
            while (rs.next()){
                CardDto cardDto= new CardDto();
                cardDto.setCardNumber(rs.getInt("card_number"));
                cardDto.setBalance(rs.getDouble("balance"));
                cardDto.setCreatedDate(rs.getTimestamp("crated_date").toLocalDateTime());
                cardDto.setExpDate(rs.getDate("exp_date").toLocalDate());
                cardDto.setCardStatus(CardStatus.valueOf(rs.getString("status")));
                cardDto.setUserPhone(rs.getString("phone"));
                cardDtoList.add(cardDto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return cardDtoList;
    }

    public void update_card(CardDto cardDto) {
        Connection connection=DBConnection.getConnection();
        String sql="update card set status=? where card_number=? ";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setString(1,cardDto.getCardStatus().toString());
            pr.setInt(2,cardDto.getCardNumber());
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean deactivate(CardDto cardDto) {
        Connection connection=DBConnection.getConnection();
        String sql="update card set status=? where card_number=?";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
        pr.setString(1,cardDto.getCardStatus().toString());
        pr.setInt(2,cardDto.getCardNumber());
           return pr.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public boolean refillCard(CardDto cardDto) {
        Connection connection=DBConnection.getConnection();
        String sql="update card set balance=? where phone=?";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setDouble(1,cardDto.getBalance());
            pr.setString(2,cardDto.getUserPhone());
            return pr.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
