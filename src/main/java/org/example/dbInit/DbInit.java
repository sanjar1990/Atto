package org.example.dbInit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.CardDto;
import org.example.dto.ProfileDto;
import org.example.enums.CardStatus;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.repository.CardRepo;
import org.example.repository.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DbInit {
    @Autowired
    private ProfileRepo profileRepo;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CardRepo cardRepo;
    public  void createCompanyCard(){
        CardDto cardDto= new CardDto();
        cardDto.setBalance(0.0);
        cardDto.setStatus(CardStatus.ACTIVE);
        cardDto.setCard_number(777);
        cardDto.setCreated_date(LocalDateTime.now());
        cardDto.setExp_date(LocalDate.of(2222,12,1));
        cardDto.setPhone("998908070176");
        CardDto isExist=cardRepo.checkCardByNum(cardDto.getCard_number());
        if(isExist!=null){
            System.out.println("Card is already exists");
        }else {
            cardRepo.create_card(cardDto);
            System.out.println("Card was created successfully");
        }
    }


    public  void createAdmin(){
        ProfileDto profileDto= new ProfileDto();
        profileDto.setRole(ProfileRole.ADMIN);
        profileDto.setName("Sanjar");
        profileDto.setSurname("Niyazov");
        profileDto.setLogin("sanjar1990");
        profileDto.setPassword("12345");
        profileDto.setPhone("998908070176");
        profileDto.setCreated_date(LocalDateTime.now());
        profileDto.setStatus(ProfileStatus.ACTIVE);
        if(profileRepo.getProfile("998908070176")!=null){
            System.out.println("This admin already exist");
        }else {

            boolean result= profileRepo.addProfile(profileDto);
       if(result){
            System.out.println("Admin created successfully");
        }else {

             System.out.println("Admin not created");
    } }
    }

    public void createProfileTable(){
        String sql="create table if not exists profile(" +
                "id bigserial primary key," +
                "name varchar(100) not null," +
                "surname varchar(100)," +
                "phone varchar(13) unique not null," +
                "password varchar(100) not null," +
                "login varchar(100) unique not null," +
                "created_date timestamp default now()," +
                "status varchar(50) not null," +
                "role varchar(50) not null  )";
        jdbcTemplate.update(sql);
    }
  public void createCardTable(){
      String sql="create table if not exists card(" +
              "card_number bigint primary key," +
              "crated_date timestamp default now()," +
              "exp_date date ," +
              "balance numeric(10,2) not null," +
              "status varchar(50) not null," +
              "phone varchar(13)," +
              "Foreign key (phone) references profile(phone))";
      jdbcTemplate.update(sql);
  }

public void createTerminalTable(){
    String sql="create table if not exists terminal(" +
            "code int primary key," +
            "address varchar(225)," +
            "status varchar(50) not null," +
            "created_date timestamp default now() )";
    jdbcTemplate.update(sql);
}
public void createTransactionTable(){
    String sql="create table if not exists transaction(" +
            "id bigserial primary key," +
            "card_number bigint not null," +
            "amount numeric(10,2) not null," +
            "terminal_code int," +
            "type varchar(50) not null," +
            "created_date timestamp default now(), " +
            "Foreign key(card_number) references card(card_number)," +
            "Foreign key(terminal_code) references terminal(code) )";
    jdbcTemplate.update(sql);
}

}
