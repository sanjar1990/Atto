package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.container.ComponentContainer;
import org.example.dto.TerminalDto;
import org.example.enums.TerminalStatus;
import org.example.repository.TerminalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TerminalService {
    @Autowired
    private TerminalRepo terminalRepo;
    public void createTerminal(int terminalCode, String address) {
        TerminalDto terminalDto=terminalRepo.checkTerminalByNum(terminalCode);
        if(terminalDto!=null){
            System.out.println("Terminal is already exist!");
            return;
        }
        terminalDto=new TerminalDto();
        terminalDto.setTerminalStatus(TerminalStatus.ACTIVE);
        terminalDto.setCreated_date(LocalDateTime.now());
        terminalDto.setTerminalCode(terminalCode);
        terminalDto.setAddress(address);
        boolean result=terminalRepo.createTerminal(terminalDto);
    if (result){
        System.out.println("Terminal was created successfully");
    }else {
        System.out.println("Something went wrong");
    }
    }

    public void getAllTerminal() {
        List<TerminalDto> terminalDtoList=terminalRepo.getAllTerminal();
        if(!terminalDtoList.isEmpty()){
            terminalDtoList.forEach(System.out::println);
        }else {
            System.out.println("Terminal not found!");
        }
    }

    public void updateTerminal(int oldCode, int newCode, String newAddress) {
        TerminalDto terminalDto=terminalRepo.checkTerminalByNum(oldCode);
        if(terminalDto==null){
            System.out.println("Terminal not found!");
            return;
        }
        boolean result=terminalRepo.updateTerminal(oldCode,newCode,newAddress);
        if(result){
            System.out.println("Terminal was updated successfully");
        }else {
            System.out.println("Something went wrong");
        }

    }

    public void changeStatus(int terminalCode, String newStatus) {
        TerminalDto terminalDto=terminalRepo.checkTerminalByNum(terminalCode);
        if(terminalDto==null){
            System.out.println("Terminal not found!");
        return;
        }
        boolean result=terminalRepo.updateTerminalStatus(terminalCode, newStatus);
        if(result){
            if(newStatus.equals(TerminalStatus.DELETED)){
                System.out.println("Terminal was deleted successfully ");
            }else {
                System.out.println("Terminal was updated successfully");
            }
        }else {
            System.out.println("Something went wrong");
        }
    }
}
