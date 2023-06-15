package org.example.repository;

import org.example.dto.ProfileDto;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ProfileRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public  boolean  addProfile(ProfileDto profileDto){
        String sql="insert into profile(name,surname, phone, password, login, created_date, status,role) values (?,?,?,?,?,?,?,?)";
        int i=jdbcTemplate.update(sql,profileDto.getName(),profileDto.getSurname(),profileDto.getPhone(),profileDto.getPassword(),
                profileDto.getLogin(),profileDto.getCreated_date(),profileDto.getStatus().name(), profileDto.getRole().name());
    return i == 1;
    }
    public ProfileDto getProfileByPhoneOrLogin(String data){
        String sql="select * from profile where login=? or phone=?";
        List<ProfileDto>profileDtoList=jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDto.class),data,data);
        return profileDtoList.isEmpty()?null:profileDtoList.get(0);
    }


    public ProfileDto login(String login, String password) {
        String sql="select * from profile where login=? and password=?";
        List<ProfileDto> profileDtoList=jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDto.class),login,password);
        return profileDtoList.isEmpty()?null:profileDtoList.get(0);
    }
    public List<ProfileDto> getProfileList() {
        String sql="select * from profile";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(ProfileDto.class));
    }
    public ProfileDto getProfile(String phone) {
        String sql="select * from profile where phone=?";
        List<ProfileDto>list=jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDto.class), phone);
        return list.isEmpty()?null:list.get(0);
    }
    public void updateProfile(ProfileDto profileDto) {
        String sql="update profile set status=? where phone=?";
        jdbcTemplate.update(sql,profileDto.getStatus().toString(),profileDto.getPhone());
    }


}
