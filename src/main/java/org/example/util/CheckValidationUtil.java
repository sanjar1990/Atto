package org.example.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.container.ComponentContainer;
import org.example.dto.CardDto;
import org.example.dto.TerminalDto;
import org.example.enums.TerminalStatus;
import org.example.repository.TerminalRepo;
import org.example.repository.UserRepo;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckValidationUtil {
    private  UserRepo userRepo;
    private TerminalRepo terminalRepo;

    public static boolean isValidPhone(String phone){
        if(phone.length()<12 || !phone.startsWith("998") ){
            System.out.println("Enter valid phone number");
            return false;
        }
        return true;
    }

    public  CardDto checkCard(int cardNum){
        CardDto cardDto=userRepo.checkCardByNum(cardNum);
        if(cardDto==null){
            System.out.println("Card was not found!");
            return null;
        } else if (!cardDto.getUserPhone().equals(ComponentContainer.profileDto.getPhone())) {
            System.out.println("Card not belongs to you");
            return null;
        }
        return cardDto;
    }

    public TerminalDto checkTerminal(int terminalNum) {
        TerminalDto terminalDto=terminalRepo.checkTerminalByNum(terminalNum);
        if(terminalDto==null){
            System.out.println("Terminal not exist");
            return null;
        }else if(!terminalDto.getTerminalStatus().equals(TerminalStatus.ACTIVE)){
            System.out.println("Terminal is out of order");
        return null;
        }
        return terminalDto;
    }

}
