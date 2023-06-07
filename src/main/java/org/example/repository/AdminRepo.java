package org.example.repository;

import org.example.dto.CardDto;
import org.example.dto.ProfileDto;
import org.example.enums.CardStatus;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.util.DBConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AdminRepo {
    public void addCommission(CardDto cardDto) {
        Connection connection=DBConnection.getConnection();
        String sql="update card set balance=? where phone=?";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setDouble(1,cardDto.getBalance());
            pr.setString(2,cardDto.getUserPhone());
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
    public CardDto getAdminCard() {
        Connection connection= DBConnection.getConnection();
        String sql="select * from card as c " +
                "inner join profile as p " +
                "on c.phone=p.phone " +
                "where p.role=?";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setString(1,ProfileRole.ADMIN.name());
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

    public boolean create_card(CardDto cardDto){
        Connection connection= DBConnection.getConnection();
        String sql="insert into card(card_number, crated_date, exp_date, balance, status, phone) values(?,?,?,?,?,?)";
        try {
            PreparedStatement pr= connection.prepareStatement(sql);
            pr.setInt(1,cardDto.getCardNumber());
            pr.setTimestamp(2, Timestamp.valueOf(cardDto.getCreatedDate()));
            pr.setDate(3,Date.valueOf(cardDto.getExpDate()));
            pr.setDouble(4,cardDto.getBalance());
            pr.setString(5,cardDto.getCardStatus().toString());
            pr.setString(6,cardDto.getUserPhone());
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

    public List<CardDto> getCardList() {
        Connection connection= DBConnection.getConnection();
        List<CardDto> cardDtoList=new LinkedList<>();
        String sql="select * from card ";
        try {
            Statement statement=connection.createStatement();

            ResultSet rs=statement.executeQuery(sql);
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

    public void updateCardExpDate(CardDto cardDto) {
        Connection connection=DBConnection.getConnection();
        String sql="update card set exp_date=? where card_number=? ";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
           pr.setDate(1,Date.valueOf(cardDto.getExpDate()));
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

//    public boolean deleteCard(int cardNum) {
//        Connection connection=DBConnection.getConnection();
//        String sql="delete from card where card_number=?";
//        try {
//            PreparedStatement pr=connection.prepareStatement(sql);
//            pr.setInt(1,cardNum);
//            return pr.executeUpdate()>0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public List<ProfileDto> getProfileList() {
        Connection connection= DBConnection.getConnection();
        List<ProfileDto> profileDtoList=new LinkedList<>();
        String sql="select * from profile";
        try {
            Statement statement=connection.createStatement();

            ResultSet rs=statement.executeQuery(sql);
            while (rs.next()){
                ProfileDto profileDto = new ProfileDto();
                profileDto.setId(rs.getInt("id"));
                profileDto.setName(rs.getString("name"));
                profileDto.setSurname(rs.getString("surname"));
                profileDto.setPhone(rs.getString("phone"));
                profileDto.setLogin(rs.getString("login"));
                profileDto.setPassword(rs.getString("password"));
                profileDto.setCreated_date(rs.getTimestamp("created_date").toLocalDateTime());
                profileDto.setStatus(ProfileStatus.valueOf(rs.getString("status")));
                profileDto.setRole(ProfileRole.valueOf(rs.getString("role")));
                profileDtoList.add(profileDto);

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
        return profileDtoList;
    }

    public ProfileDto getProfile(String phone) {
        String sql="select * from profile where phone=?";
        Connection connection=DBConnection.getConnection();
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setString(1,phone);
            ResultSet rs=pr.executeQuery();
            if(rs.next()){
                ProfileDto profileDto = new ProfileDto();
                profileDto.setId(rs.getInt("id"));
                profileDto.setName(rs.getString("name"));
                profileDto.setSurname(rs.getString("surname"));
                profileDto.setPhone(rs.getString("phone"));
                profileDto.setLogin(rs.getString("login"));
                profileDto.setPassword(rs.getString("password"));
                profileDto.setCreated_date(rs.getTimestamp("created_date").toLocalDateTime());
                profileDto.setStatus(ProfileStatus.valueOf(rs.getString("status")));
                profileDto.setRole(ProfileRole.valueOf(rs.getString("role")));
                return profileDto;
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

    public void updateProfile(ProfileDto profileDto) {
        Connection connection=DBConnection.getConnection();
        String sql="update profile set status=? where phone=?";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setString(1,profileDto.getStatus().toString());
            pr.setString(2,profileDto.getPhone());
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

    public CardDto getCardBalance(String phone) {
        String sql="select * from card where phone=?";
        Connection connection=DBConnection.getConnection();
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setString(1,phone);
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

    public ProfileDto getAdminProfile() {
        String sql="select * from profile where role=?";
        Connection connection=DBConnection.getConnection();
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setString(1,ProfileRole.ADMIN.name());
            ResultSet rs=pr.executeQuery();
            if(rs.next()){
                    ProfileDto profileDto = new ProfileDto();
                    profileDto.setId(rs.getInt("id"));
                    profileDto.setName(rs.getString("name"));
                    profileDto.setSurname(rs.getString("surname"));
                    profileDto.setPhone(rs.getString("phone"));
                    profileDto.setLogin(rs.getString("login"));
                    profileDto.setPassword(rs.getString("password"));
                    profileDto.setCreated_date(rs.getTimestamp("created_date").toLocalDateTime());
                    profileDto.setStatus(ProfileStatus.valueOf(rs.getString("status")));
                    profileDto.setRole(ProfileRole.valueOf(rs.getString("role")));
                    return profileDto;

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
}

