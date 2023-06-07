package org.example.repository;

import org.example.dto.ProfileDto;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.util.DBConnection;

import java.sql.*;

public class ProfileRepo {
    public  boolean  addProfile(ProfileDto profileDto){
        Connection connection= DBConnection.getConnection();
        String sql="insert into profile(name,surname, phone, password, login, created_date, status,role)" +
                "values(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr= connection.prepareStatement(sql);
            pr.setString(1,profileDto.getName());
            pr.setString(2,profileDto.getSurname());
            pr.setString(3,profileDto.getPhone());
            pr.setString(4,profileDto.getPassword());
            pr.setString(5,profileDto.getLogin());
            pr.setTimestamp(6, Timestamp.valueOf(profileDto.getCreated_date()));
            pr.setString(7,profileDto.getStatus().toString());
            pr.setString(8,profileDto.getRole().toString());
            pr.executeUpdate();
            return true;
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
    public ProfileDto getProfileByLogin(String data){
        Connection connection = null;

        String sql="select * from profile where login=? or phone=?";
        try {
            connection= DBConnection.getConnection();
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1,data);
            ps.setString(2,data);
            ResultSet  resultSet =ps.executeQuery();
            if (resultSet.next()){
                ProfileDto profileDto=new ProfileDto();
                profileDto.setId(resultSet.getInt("id"));
                profileDto.setName(resultSet.getString("name"));
                profileDto.setSurname(resultSet.getString("surname"));
                profileDto.setPhone(resultSet.getString("phone"));
                profileDto.setLogin(resultSet.getString("login"));
                profileDto.setPassword(resultSet.getString("password"));
                profileDto.setStatus(ProfileStatus.valueOf(resultSet.getString("status")));
                profileDto.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                profileDto.setRole(ProfileRole.valueOf(resultSet.getString("role")));
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
    public ProfileDto getProfileByPhone(String data){
        Connection connection =DBConnection.getConnection();;

        String sql="select * from profile where login=? or phone=?";
        try {

            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1,data);
            ps.setString(2,data);
            ResultSet  resultSet =ps.executeQuery();
            if (resultSet.next()){
                ProfileDto profileDto=new ProfileDto();
                profileDto.setId(resultSet.getInt("id"));
                profileDto.setName(resultSet.getString("name"));
                profileDto.setSurname(resultSet.getString("surname"));
                profileDto.setPhone(resultSet.getString("phone"));
                profileDto.setLogin(resultSet.getString("login"));
                profileDto.setPassword(resultSet.getString("password"));
                profileDto.setStatus(ProfileStatus.valueOf(resultSet.getString("status")));
                profileDto.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                profileDto.setRole(ProfileRole.valueOf(resultSet.getString("role")));
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

    public ProfileDto login(String login, String password) {
        Connection connection= DBConnection.getConnection();
        String sql="select * from profile where login=? and password=?";
        try {
            PreparedStatement pr=connection.prepareStatement(sql);
            pr.setString(1,login);
            pr.setString(2,password);
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
