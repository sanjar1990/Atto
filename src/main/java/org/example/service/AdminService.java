package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.container.ComponentContainer;
import org.example.dto.CardDto;
import org.example.dto.ProfileDto;
import org.example.dto.TransactionDto;
import org.example.enums.CardStatus;
import org.example.enums.ProfileStatus;
import org.example.enums.TransactionType;
import org.example.repository.AdminRepo;
import org.example.repository.TransactionRepo;
import org.example.repository.UserRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminService {
    private AdminRepo adminRepo;
    private UserRepo userRepo;
    private TransactionRepo transactionRepo;
    public void createCard(int cardNum, int year ,int month){
        //Check card
        if(adminRepo.checkCardByNum(cardNum)!=null){
            System.out.println("This card is already created!");
        }
        //create new card
            CardDto cardDto= new CardDto();
            cardDto.setCardStatus(CardStatus.NOT_ACTIVE);
            cardDto.setCardNumber(cardNum);
            cardDto.setCreatedDate(LocalDateTime.now());
            cardDto.setExpDate(LocalDate.of(year,month,1));
            cardDto.setBalance(0.0);
            if (adminRepo.create_card(cardDto)) {
                System.out.println("You have created card successfully");
            }else {
                System.out.println("something went wrong");
            }
    }
    public void getAllCardList() {
        List<CardDto> cardDtoList=adminRepo.getCardList();
        if(cardDtoList!=null){
            cardDtoList.forEach(System.out::println);
        }else {
            System.out.println("there is no card");
        }
    }

    public void updateCardExpDate(int cardNum, int year, int month) {
        CardDto cardDto=adminRepo.checkCardByNum(cardNum);
        if (cardDto!=null){
            cardDto.setExpDate(LocalDate.of(year,month,1));
            adminRepo.updateCardExpDate(cardDto);
            System.out.println("Card was updated Successfully");
        }else {
            System.out.println("Not found card with this number!");
        }
    }
    public void changeStatus(int cardNum, String status) {
        CardDto cardDto=adminRepo.checkCardByNum(cardNum);
        if (cardDto!=null){
            cardDto.setCardStatus(CardStatus.valueOf(status));
           adminRepo.update_card(cardDto);
            System.out.println("Card status was changed to: "+status);
        }else {
            System.out.println("Not found card with this number!");
        }
    }
    public void deleteCard(int cardNum) {
        CardDto cardDto=adminRepo.checkCardByNum(cardNum);
        if(cardDto==null){
            System.out.println("Card was not found!");
            return;
        }
        cardDto.setCardStatus(CardStatus.DELETED);
        boolean result=userRepo.deactivate(cardDto);
        if(result){
            System.out.println("Card was deleted successfully");
        }else {
            System.out.println("Something went wrong");
        }
    }
    public void getAllProfile() {
        List<ProfileDto> profileDtoList=adminRepo.getProfileList();
        if(profileDtoList !=null){
            profileDtoList.forEach(System.out::println);
        }else {
            System.out.println("Profile was not found");
        }
    }

    public void changeProfileStatus(String phone, String status) {
        ProfileDto profileDto=adminRepo.getProfile(phone);
        if(profileDto!=null){
            profileDto.setStatus(ProfileStatus.valueOf(status));
            System.out.println("Profile status was changed to: "+status);
           adminRepo.updateProfile(profileDto);
        }else {
            System.out.println("This profile was not found");
        }
    }

    public void getCompanyCardBalance(String phone) {
        CardDto cardDto=adminRepo.getCardBalance(phone);
        if(cardDto !=null){
            System.out.println(cardDto.getBalance());
        }else {
            System.out.println("Card was not found!");
        }
    }

    public void setCommission(double commission) {
        CardDto cardDto=adminRepo.getAdminCard();
        if(cardDto==null){
            System.out.println("Admin Card not found!");
       return;
        }
        //admin cardga komissiya qo'shish
        cardDto.setBalance(cardDto.getBalance()+commission);
        adminRepo.addCommission(cardDto);
        //transacsiya istoriya yozish
        TransactionDto transactionDto= new TransactionDto();
        transactionDto.setTransactionType(TransactionType.COMMISSION);
        transactionDto.setCardNumber(cardDto.getCardNumber());
        transactionDto.setAmount(commission);
        transactionDto.setCreatedDate(LocalDateTime.now());
        transactionRepo.addTransactionInfo(transactionDto);
    }
}

