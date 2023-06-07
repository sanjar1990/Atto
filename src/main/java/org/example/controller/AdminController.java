package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.container.ComponentContainer;
import org.example.enums.CardStatus;
import org.example.enums.ProfileStatus;
import org.example.enums.TerminalStatus;
import org.example.service.AdminService;
import org.example.service.TerminalService;
import org.example.service.TransactionService;
import org.example.util.GetAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminController {
    private AdminService adminService;
    private TransactionService transactionService;
    private TerminalService terminalService;
    public void start(){
        boolean t=true;
        while (t){
            show();
            switch (GetAction.getAction()){
                case 0->t=false;
                case 1->createCard();
                case 2->getCardList();
                case 3->updateCard();
                case 4->changeCardStatus();
                case 5->deleteCard();
                case 6->createTerminal();
                case 7->getTerminalList();
                case 8->updateTerminal();
                case 9->changeTerminalStatus();
                case 10->deleteTerminal();
                case 11->getProfileList();
                case 12->changeProfileStatus();
                case 13->getAllTransaction();
                case 14->getCompanyCardBalance();
                case 15->todayTransaction();
                case 16->dailyTransaction();
                case 17->getTransactionBetween();
                case 18->totalCompanyBalance();
                case 19->transactionByTerminal();
                case 20->transactionHistoryByCard();
                default -> System.out.println("Query was not found!");
            }
        }
    }
    public  void show(){
        System.out.println("*** Admin Menu ***\n" +
                "    (Card)\n" +
                "    1. Create Card(number,exp_date)\n" +
                "    2. Card List\n" +
                "    3. Update Card (number,exp_date)\n" +
                "    4. Change Card status\n" +
                "    5. Delete Card\n" +
                "\n" +
                "    (Terminal)\n" +
                "    6. Create Terminal (code unique,address)\n" +
                "    7. Terminal List\n" +
                "    8. Update Terminal (code,address)\n" +
                "    9. Change Terminal Status\n" +
                "    10. Delete\n" +
                "\n" +
                "    (Profile)\n" +
                "    11. Profile List\n" +
                "    12. Change Profile Status (by phone)\n" +
                "\n" +
                "    (Transaction)\n" +
                "    13. Transaction List\n" +
                "        CardNumber, TerminalNumber, Amount,TransactionDate,Type (oxirgi birinchi ko'rinadi)\n" +
                "    14. Company Card Balance\n" +
                "        (card bo'ladi shu cardga to'lovlar tushadi. bu sql da insert qilinga bo'ladi)\n" +
                "\n" +
                "       (Statistic)\n" +
                "    15. Bugungi to'lovlar\n" +
                "         CardNumber, TerminalNumber, Amount,TransactionDate,Type (oxirgi birinchi ko'rinadi)\n" +
                "    16. Kunlik to'lovlar (bir kunlik to'lovlar):\n" +
                "        Enter Date: yyyy-MM-dd\n" +
                "          CardNumber, TerminalNumber, Amount,TransactionDate,Type (oxirgi birinchi ko'rinadi)\n" +
                "    17. Oraliq to'lovlar:\n" +
                "        Enter FromDate: yyyy-MM-dd\n" +
                "        Enter ToDate:   yyyy-MM-dd\n" +
                "    18. Umumiy balance (company card dagi pulchalar)\n" +
                "    19. Transaction by Terminal:\n" +
                "        Enter terminal number:\n" +
                "    20. Transaction By Card:\n" +
                "        Enter Card number:" +
                "     0. Exit"      );
    }

    public void createCard(){
        System.out.println("Enter card number:");
        int cardNum= ComponentContainer.intScanner.nextInt();
        System.out.println("Enter expire date:");
        System.out.println("Enter year:");
        int year= ComponentContainer.intScanner.nextInt();
        System.out.println("Enter month by number: ");
        int month= ComponentContainer.intScanner.nextInt();
        adminService.createCard(cardNum,year,month);
    }
    public void getCardList(){
      adminService.getAllCardList();
    }
    public void updateCard(){
        System.out.println("Enter card number:");
        int cardNum= ComponentContainer.intScanner.nextInt();
        System.out.println("Enter expire date:");
        System.out.println("Enter year:");
        int year= ComponentContainer.intScanner.nextInt();
        System.out.println("Enter month by number: ");
        int month= ComponentContainer.intScanner.nextInt();
       adminService.updateCardExpDate(cardNum,year,month);
    }
    private void changeCardStatus() {
        System.out.println("Enter card number:");
        int cardNum= ComponentContainer.intScanner.nextInt();
        System.out.println("Enter card status number:");
        System.out.println("1=ACTIVE, 2=NOT_ACTIVE, 3=BLOCKED, 4=EXPIRED, 5=DELETED");
        int statusNum=ComponentContainer.intScanner.nextInt();
        String status="";
        switch (statusNum){
            case 1-> status= CardStatus.ACTIVE.toString();
            case 2-> status=CardStatus.NOT_ACTIVE.toString();
            case 3-> status=CardStatus.BLOCKED.toString();
            case 4-> status=CardStatus.EXPIRED.name();
            case 5-> status=CardStatus.DELETED.name();
        }
      adminService.changeStatus(cardNum,status);
    }
    private void deleteCard() {
        System.out.println("Enter card number:");
        int cardNum= ComponentContainer.intScanner.nextInt();
        adminService.deleteCard(cardNum);
    }
    private void getProfileList() {
        adminService.getAllProfile();
    }
    private void changeProfileStatus() {
        System.out.println("Enter profile phone:");
        String phone=ComponentContainer.stringScanner.nextLine();
        System.out.println("Enter status number: ");
        System.out.println("1=ACTIVE, 2=NOT_ACTIVE ,3=BLOCKED");
        int statusNum=ComponentContainer.intScanner.nextInt();
        String status="";
        switch (statusNum){
            case 1-> status=ProfileStatus.ACTIVE.toString();
            case 2-> status=ProfileStatus.NOT_ACTIVE.toString();
            case 3-> status=ProfileStatus.BLOCKED.toString();
        }
        adminService.changeProfileStatus(phone, status);
    }
    private void createTerminal(){
        System.out.println("Enter terminal code: ");
        int terminalCode=ComponentContainer.intScanner.nextInt();
        System.out.println("Enter address: ");
        String address=ComponentContainer.stringScanner.nextLine();
       terminalService.createTerminal(terminalCode,address);
    }
    private void getTerminalList() {
        terminalService.getAllTerminal();
    }

    private void updateTerminal() {
        System.out.println("Enter code existing terminal: ");
        int oldCode=ComponentContainer.intScanner.nextInt();
        System.out.println("Enter new code: ");
        int newCode=ComponentContainer.intScanner.nextInt();
        System.out.println("Enter new address: ");
        String newAddress=ComponentContainer.stringScanner.nextLine();
       terminalService.updateTerminal(oldCode, newCode,newAddress);
    }
    private void changeTerminalStatus() {
        System.out.println("Enter terminal code: ");
        int terminalCode=ComponentContainer.intScanner.nextInt();
        System.out.println("Enter choose status: ");
        System.out.println(" 1=ACTIVE , 2=NOT_ACTIVE, 3=OUT_OF_ORDER, 4=DELETED");
        int statusNum=ComponentContainer.intScanner.nextInt();
        String newStatus="";
        switch (statusNum){
            case 1-> newStatus=TerminalStatus.ACTIVE.name();
            case 2-> newStatus=TerminalStatus.NOT_ACTIVE.name();
            case 3-> newStatus=TerminalStatus.OUT_OF_ORDER.name();
            case 4-> newStatus=TerminalStatus.DELETED.name();
        }
     terminalService.changeStatus(terminalCode,newStatus);
    }
    private void deleteTerminal() {
        System.out.println("Enter terminal code to delete: ");
        int terminalCode=ComponentContainer.intScanner.nextInt();
       terminalService.changeStatus(terminalCode,TerminalStatus.DELETED.name());
    }
    private void getAllTransaction() {
       transactionService.getAllTransactionHistory();
    }
    private void getCompanyCardBalance() {
   adminService.getCompanyCardBalance(ComponentContainer.profileDto.getPhone());
    }
    private void todayTransaction() {
     transactionService.getTransactionByDay(LocalDate.now());
    }
    private void dailyTransaction() {
        System.out.println("Enter year: ");
        int year=ComponentContainer.intScanner.nextInt();
        System.out.println("Enter month: ");
        int month=ComponentContainer.intScanner.nextInt();
        System.out.println("Enter day: ");
        int day=ComponentContainer.intScanner.nextInt();
        LocalDate localDate=LocalDate.of(year,month,day);
        transactionService.getTransactionByDay(localDate);
    }

    private void getTransactionBetween() {
        System.out.println("Enter year from: ");
        int fromYear=ComponentContainer.intScanner.nextInt();
        System.out.println("Enter month from: ");
        int fromMonth=ComponentContainer.intScanner.nextInt();
        System.out.println("Enter day from: ");
        int fromDay=ComponentContainer.intScanner.nextInt();
        System.out.println("Enter year To: ");
        int toYear=ComponentContainer.intScanner.nextInt();
        System.out.println("Enter month To: ");
        int toMonth=ComponentContainer.intScanner.nextInt();
        System.out.println("Enter day To: ");
        int toDay=ComponentContainer.intScanner.nextInt();
        LocalDate fromDate=LocalDate.of(fromYear,fromMonth,fromDay);
        LocalDate toDate=LocalDate.of(toYear,toMonth,toDay);
        transactionService.getTransactionBetween(fromDate, toDate);
    }
    private void totalCompanyBalance() {
       adminService.getCompanyCardBalance(ComponentContainer.profileDto.getPhone());
    }
    private void transactionByTerminal() {
        System.out.println("Enter terminal code: ");
        int terminalCode=ComponentContainer.intScanner.nextInt();
    transactionService.getTransactionByTerminal(terminalCode);
    }
    private void transactionHistoryByCard() {
        System.out.println("Enter Card number:");
        int cardNum=ComponentContainer.intScanner.nextInt();
        transactionService.getTransactionByCard(cardNum);
    }
}
