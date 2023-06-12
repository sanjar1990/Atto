package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.container.ComponentContainer;
import org.example.dto.CardDto;
import org.example.dto.TerminalDto;
import org.example.dto.TransactionDto;
import org.example.enums.TransactionType;
import org.example.repository.AdminRepo;
import org.example.repository.TerminalRepo;
import org.example.repository.TransactionRepo;
import org.example.repository.UserRepo;
import org.example.util.CheckValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TransactionService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private TerminalRepo terminalRepo;
    @Autowired
    private CheckValidationUtil checkValidationUtil;

    public void refillCard(int cardNum, double amount) {
        //Check
        CardDto cardDto= checkValidationUtil.checkCard(cardNum);
        if(cardDto==null) return;
        cardDto.setBalance(cardDto.getBalance()+amount);
        //commissiya qo'shish
        adminService.setCommission(amount*0.05);
        //user cardga balance qo'shish
        boolean result=userRepo.refillCard(cardDto);
        //transaction history
        TransactionDto transactionDto= new TransactionDto();
        transactionDto.setTransactionType(TransactionType.REFILL);
        transactionDto.setCardNumber(cardDto.getCardNumber());
        transactionDto.setAmount(amount);
        transactionDto.setCreatedDate(LocalDateTime.now());
        transactionRepo.addTransactionInfo(transactionDto);
        if(result){
            System.out.println("You successfully deposited money to your card");
        }else {
            System.out.println("Something went wrong");
        }
    }
    public void getUserTransactionHistory() {
        List<TransactionDto> transactionDtoList=transactionRepo.getUserTransactionHistory(ComponentContainer.profileDto.getPhone());
   if(!transactionDtoList.isEmpty()){
       transactionDtoList.forEach(System.out::println);
   }else {
       System.out.println("No transaction history");
   }
    }
    public void makePayment(int cardNum, int terminalNum) {
        //Check card
        CardDto cardDto= checkValidationUtil.checkCard(cardNum);
        //Check terminal
        TerminalDto terminalDto=checkValidationUtil.checkTerminal(terminalNum);
        if(cardDto==null || terminalDto ==null){
            return;
        }
        //user cardan fare ayirish
        cardDto.setBalance(cardDto.getBalance()-ComponentContainer.fare);
        boolean result =userRepo.refillCard(cardDto);
        // user Transaction history
        TransactionDto transactionDto= new TransactionDto();
        transactionDto.setCardNumber(cardDto.getCardNumber());
        transactionDto.setTransactionType(TransactionType.PAYMENT);
        transactionDto.setAmount(ComponentContainer.fare);
        transactionDto.setCreatedDate(LocalDateTime.now());
        transactionDto.setTerminalCode(terminalDto.getTerminalCode());
        transactionRepo.addTransactionInfo(transactionDto);
        //admin cardga pul qo'shish
        CardDto adminCard= adminRepo.getAdminCard();
        if(adminCard==null){
            System.out.println("Admin Card not found!");
            return;
        }
        adminCard.setBalance(adminCard.getBalance()+ComponentContainer.fare);
        userRepo.refillCard(adminCard);
       //Admin Card Transaction yozish
//        TransactionDto adminTransaction= new TransactionDto();
        transactionDto.setCardNumber(adminCard.getCardNumber());
        transactionDto.setTransactionType(TransactionType.PAYMENT);
        transactionDto.setAmount(ComponentContainer.fare);
        transactionDto.setCreatedDate(LocalDateTime.now());
        transactionDto.setTerminalCode(terminalDto.getTerminalCode());
        transactionRepo.addTransactionInfo(transactionDto);
        if(result){
            System.out.println("Payment was completed successful");
        }else {
            System.out.println("Something went wrong");
        }
    }
    public void getAllTransactionHistory() {
        List<TransactionDto> transactionDtoList=transactionRepo.getAllTransactionHistory();
        if(!transactionDtoList.isEmpty()){
            transactionDtoList.forEach(System.out::println);
        }else {
            System.out.println("No transaction history");
        }
    }

    public void getTransactionByDay(LocalDate day) {
        List<TransactionDto> transactionDtoList=transactionRepo.getTodayTransaction(day);
        if(!transactionDtoList.isEmpty()){
            transactionDtoList.forEach(System.out::println);
        }else {
            System.out.println("Not found today's transaction history!");
        }
    }
    public void getTransactionBetween(LocalDate fromDate, LocalDate toDate) {
        List<TransactionDto> transactionDtoList=transactionRepo.getTransactionBetween(fromDate, toDate);
        if(!transactionDtoList.isEmpty()){
            transactionDtoList.forEach(System.out::println);
        }else {
            System.out.println("Not found today's transaction history!");
        }
    }
    public void getTransactionByTerminal(int terminalCode) {
        TerminalDto terminalDto=terminalRepo.checkTerminalByNum(terminalCode);
        if(terminalDto==null){
            System.out.println("Terminal was not found");
        return;
        }
        List<TransactionDto> transactionDtoList=transactionRepo.getTransactionByTerminal(terminalDto);
        if (transactionDtoList.isEmpty()){
            System.out.println("No transaction history was found by this terminal!");
        }else {
            transactionDtoList.forEach(System.out::println);
        }
    }
    public void getTransactionByCard(int cardNum) {
        CardDto cardDto=checkValidationUtil.checkCard(cardNum);
        if(cardDto==null){
            return;
        }
        List<TransactionDto> transactionDtoList=transactionRepo.getTransactionByCard(cardDto);
        if (transactionDtoList.isEmpty()){
            System.out.println("No transaction history was found by this Card!");
        }else {
            transactionDtoList.forEach(System.out::println);
        }
    }
}
