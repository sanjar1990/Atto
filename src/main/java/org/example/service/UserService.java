package org.example.service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.container.ComponentContainer;
import org.example.dto.CardDto;
import org.example.enums.CardStatus;
import org.example.repository.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserService {
    @Autowired
    private CardRepo cardRepo;
    public void addCard(int cardNum, int year, int month){
        //Check valid Card
       CardDto existCard= cardRepo.checkCardByNum(cardNum);
        if(existCard==null){
            System.out.println("This card not exist!");
            return;
        }else  if( !existCard.getStatus().equals(CardStatus.NOT_ACTIVE) ){
            System.out.println("This card is already registered by other user!");
            return;
        }else if(existCard.getExp_date().getYear()!=year && existCard.getExp_date().getMonthValue()!=month){
            System.out.println("enter valid expire date");
            return;
        }
        // Add card to user
            existCard.setStatus(CardStatus.ACTIVE);
            existCard.setPhone(ComponentContainer.profileDto.getPhone());
//        System.out.println(existCard);
        boolean result=cardRepo.add_card(existCard);
            if (result) {
                System.out.println("You have successfully added your card ");
            }else {
                System.out.println("something went wrong");
            }
    }
    public void getCardList(String phone) {
        List<CardDto> cardDtoList=cardRepo.getCardList(phone);
        if(!cardDtoList.isEmpty()){
            cardDtoList.forEach((s)->{
                if(s.getStatus().equals(CardStatus.ACTIVE)){
                    System.out.println(s);
                }
            });
        }else {
            System.out.println("you don't have any cards");
        }
    }
    public void cardChangeStatus(int cardNum) {
        CardDto cardDto=cardRepo.checkCardByNum(cardNum);
        if (cardDto!=null && cardDto.getPhone().equals(ComponentContainer.profileDto.getPhone())){
            if(cardDto.getStatus().equals(CardStatus.ACTIVE)){
                cardDto.setStatus(CardStatus.NOT_ACTIVE);
                System.out.println("Card status Changed to No Active");
            }else if(cardDto.getStatus().equals(CardStatus.NOT_ACTIVE)) {
                cardDto.setStatus(CardStatus.ACTIVE);
                System.out.println("Card status Changed to Active");
            }
           cardRepo.update_card(cardDto);
        } else {
            System.out.println("Not found card with this number!");
        }
    }
    public void deleteCard(int cardNum) {
        //check card
        CardDto cardDto=cardRepo.checkCardByNum(cardNum);
        if(cardDto==null){
            System.out.println("Card was not found!");
            return;
        } else if (!cardDto.getPhone().equals(ComponentContainer.profileDto.getPhone())) {
            System.out.println("Card not belongs to you");
            return;
        }
        //delete
            cardDto.setStatus(CardStatus.DELETED);
            cardRepo.update_card(cardDto);
            System.out.println("Card was deleted successfully");
    }


}
