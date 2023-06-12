package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.container.ComponentContainer;
import org.example.service.TransactionService;
import org.example.service.UserService;
import org.example.util.GetAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    public void start(){
        boolean t=true;
        while (t){
            show();
            switch (GetAction.getAction()){
                case 1->addCard();
                case 2-> cardList();
                case 3->cardChangeStatus();
                case 4->deleteCard();
                case 5->refillCard();
                case 6->transactionHistory();
                case 7->makePayment();
                case 0-> t=false;
            }
        }
    }

    public void show(){
        System.out.println("*** User  Menu **\n" +
                "    1. Add Card \n" +
                "    2. Card List \n" +
                "    3. Card Change Status\n" +
                "    4. Delete Card (visible_user = false,deleted_user)\n" +
                "    5. ReFill \n" +
                "    6. Transaction history\n" +
                "    7. Make Payment" +
                "    0. Exit");
    }
    public void addCard(){
        System.out.println("Enter card number:");
        int cardNum= ComponentContainer.intScanner.nextInt();
        System.out.println("Enter expire date:");
        System.out.println("Enter year:");
        int year= ComponentContainer.intScanner.nextInt();
        System.out.println("Enter month by number: ");
        int month= ComponentContainer.intScanner.nextInt();
      userService.addCard(cardNum,year,month);
    }
    public void cardList(){
        userService.getCardList(ComponentContainer.profileDto.getPhone());
    }
    public void cardChangeStatus(){
        System.out.println("Enter card number to change status:");
        int cardNum=ComponentContainer.intScanner.nextInt();
        userService.cardChangeStatus(cardNum);
    }
    private void deleteCard() {
        System.out.println("Enter card number to delete: ");
        int cardNum=ComponentContainer.intScanner.nextInt();
       userService.deleteCard(cardNum);
    }
    private void refillCard() {
        System.out.println("Enter card number to deposit: ");
        int cardNum=ComponentContainer.intScanner.nextInt();
        System.out.println("Enter amount:");
        double amount=ComponentContainer.doubleScanner.nextInt();
       transactionService.refillCard(cardNum, amount);

    }
    private void transactionHistory() {
    transactionService.getUserTransactionHistory();
    }
    private void makePayment() {
        System.out.println("Enter your card number:");
        int cardNum=ComponentContainer.intScanner.nextInt();
        System.out.println("Enter terminal number: ");
        int terminalNum=ComponentContainer.intScanner.nextInt();
       transactionService.makePayment(cardNum,terminalNum);
    }

}
