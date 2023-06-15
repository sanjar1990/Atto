package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.CardDto;
import org.example.dto.ProfileDto;
import org.example.dto.TransactionDto;
import org.example.enums.CardStatus;
import org.example.enums.ProfileStatus;
import org.example.enums.TransactionType;
import org.example.repository.CardRepo;
import org.example.repository.ProfileRepo;
import org.example.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AdminService {
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private CardRepo cardRepo;
    @Autowired
    private ProfileRepo profileRepo;

    public void createCard(int cardNum, int year ,int month){
        //Check card
        if(cardRepo.checkCardByNum(cardNum)!=null){
            System.out.println("This card is already created!");
        }
        //create new card
            CardDto cardDto= new CardDto();
            cardDto.setStatus(CardStatus.NOT_ACTIVE);
            cardDto.setCard_number(cardNum);
            cardDto.setCreated_date(LocalDateTime.now());
            cardDto.setExp_date(LocalDate.of(year,month,1));
            cardDto.setBalance(0.0);
            if (cardRepo.create_card(cardDto)) {
                System.out.println("You have created card successfully");
            }else {
                System.out.println("something went wrong");
            }
    }
    public void getAllCardList() {
        List<CardDto> cardDtoList=cardRepo.getCardList();
        if(cardDtoList!=null){
            cardDtoList.forEach(System.out::println);
        }else {
            System.out.println("there is no card");
        }
    }

    public void updateCardExpDate(int cardNum, int year, int month) {
        CardDto cardDto=cardRepo.checkCardByNum(cardNum);
        if (cardDto!=null){
            cardDto.setExp_date(LocalDate.of(year,month,1));
            cardRepo.updateCardExpDate(cardDto);
            System.out.println("Card was updated Successfully");
        }else {
            System.out.println("Not found card with this number!");
        }
    }
    public void changeStatus(int cardNum, String status) {
        CardDto cardDto=cardRepo.checkCardByNum(cardNum);
        if (cardDto!=null){
            cardDto.setStatus(CardStatus.valueOf(status));
           cardRepo.update_card(cardDto);
            System.out.println("Card status was changed to: "+status);
        }else {
            System.out.println("Not found card with this number!");
        }
    }
    public void deleteCard(int cardNum) {
        CardDto cardDto=cardRepo.checkCardByNum(cardNum);
        if(cardDto==null){
            System.out.println("Card was not found!");
            return;
        }
        cardDto.setStatus(CardStatus.DELETED);
       cardRepo.update_card(cardDto);
        System.out.println("Card was deleted successfully");

    }
    public void getAllProfile() {
        List<ProfileDto> profileDtoList=profileRepo.getProfileList();
        if(profileDtoList !=null){
            profileDtoList.forEach(System.out::println);
        }else {
            System.out.println("Profile was not found");
        }
    }

    public void changeProfileStatus(String phone, String status) {
        ProfileDto profileDto=profileRepo.getProfile(phone);
        if(profileDto!=null){
            profileDto.setStatus(ProfileStatus.valueOf(status));
            System.out.println("Profile status was changed to: "+status);
           profileRepo.updateProfile(profileDto);
        }else {
            System.out.println("This profile was not found");
        }
    }

    public void getCompanyCardBalance(String phone) {
        CardDto cardDto=cardRepo.getCardBalance(phone);
        if(cardDto !=null){
            System.out.println(cardDto.getBalance());
        }else {
            System.out.println("Card was not found!");
        }
    }

    public void setCommission(double commission) {
        CardDto cardDto=cardRepo.getAdminCard();
        if(cardDto==null){
            System.out.println("Admin Card not found!");
       return;
        }
        //admin cardga komissiya qo'shish
        cardDto.setBalance(cardDto.getBalance()+commission);
        cardRepo.addCommission(cardDto);
        //transacsiya istoriya yozish
        TransactionDto transactionDto= new TransactionDto();
        transactionDto.setType(TransactionType.COMMISSION);
        transactionDto.setCard_number(cardDto.getCard_number());
        transactionDto.setAmount(commission);
        transactionDto.setCreated_date(LocalDateTime.now());
        transactionRepo.addTransactionInfo(transactionDto);
    }
}

