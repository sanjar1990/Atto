package org.example.service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.container.ComponentContainer;
import org.example.dto.CardDto;
import org.example.enums.CardStatus;
import org.example.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserService {
    @Autowired
    private UserRepo userRepo;
    public void addCard(int cardNum, int year, int month){
        //Check valid Card
       CardDto existCard= userRepo.checkCardByNum(cardNum);
        if(existCard==null){
            System.out.println("This card not exist!");
            return;
        }else  if( !existCard.getCardStatus().equals(CardStatus.NOT_ACTIVE) ){
            System.out.println("This card is already registered by other user!");
            return;
        }else if(existCard.getExpDate().getYear()!=year && existCard.getExpDate().getMonthValue()!=month){
            System.out.println("enter valid expire date");
            return;
        }
        // Add card to user
            existCard.setCardStatus(CardStatus.ACTIVE);
            existCard.setUserPhone(ComponentContainer.profileDto.getPhone());
        System.out.println(existCard);
        boolean result=userRepo.add_card(existCard);
            if (result) {
                System.out.println("You have successfully added your card ");
            }else {
                System.out.println("something went wrong");
            }
    }
    public void getCardList(String phone) {
        List<CardDto> cardDtoList=userRepo.getCardList(phone);
        if(!cardDtoList.isEmpty()){
            cardDtoList.forEach((s)->{
                if(s.getCardStatus().equals(CardStatus.ACTIVE)){
                    System.out.println(s);
                }
            });
        }else {
            System.out.println("you don't have any cards");
        }
    }
    public void cardChangeStatus(int cardNum) {
        CardDto cardDto=userRepo.checkCardByNum(cardNum);
        if (cardDto!=null && cardDto.getUserPhone().equals(ComponentContainer.profileDto.getPhone())){
            if(cardDto.getCardStatus().equals(CardStatus.ACTIVE)){
                cardDto.setCardStatus(CardStatus.NOT_ACTIVE);
                System.out.println("Card status Changed to No Active");
            }else if(cardDto.getCardStatus().equals(CardStatus.NOT_ACTIVE)) {
                cardDto.setCardStatus(CardStatus.ACTIVE);
                System.out.println("Card status Changed to Active");
            }
           userRepo.update_card(cardDto);
        } else {
            System.out.println("Not found card with this number!");
        }
    }
    public void deleteCard(int cardNum) {
        //check card
        CardDto cardDto=userRepo.checkCardByNum(cardNum);
        if(cardDto==null){
            System.out.println("Card was not found!");
            return;
        } else if (cardDto.getUserPhone().equals(ComponentContainer.profileDto.getPhone())) {
            System.out.println("Card not belongs to you");
            return;
        }
        //delete
            cardDto.setCardStatus(CardStatus.DELETED);
            userRepo.deactivate(cardDto);
            System.out.println("Card was deleted successfully");
    }


}
