package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.container.ComponentContainer;
import org.example.dto.ProfileDto;
import org.example.service.ProfileService;
import org.example.util.GetAction;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class MenuController {
    @Autowired
    private ProfileService profileService;
    public void start(){
        boolean t=true;
        while (t){
            show();
            switch (GetAction.getAction()){
                case 1->login();
                case 2->registration();
                case 0 ->t=false;
            }
        }
    }
    public void show(){
        System.out.println("**MENU**");
        System.out.println("1. Login.");
        System.out.println("2. Registration.");
        System.out.println("0. Exit.");
    }
    public void registration(){
        System.out.println("Enter your name: ");
        String name= ComponentContainer.stringScanner.nextLine();
        System.out.println("Enter your surname:");
        String surname= ComponentContainer.stringScanner.nextLine();
        System.out.println("Enter your phone:");
        String phone= ComponentContainer.stringScanner.nextLine();
        System.out.println("Enter your login:");
        String login= ComponentContainer.stringScanner.nextLine();
        System.out.println("Enter your password:");
        String password= ComponentContainer.stringScanner.nextLine();
        ProfileDto profileDto= new ProfileDto();
        profileDto.setName(name);
        profileDto.setSurname(surname);
        profileDto.setPhone(phone);
        profileDto.setLogin(login);
        profileDto.setPassword(password);
        profileService.register(profileDto);
    }
    public void login(){
        System.out.println("Enter your login: ");
        String login=ComponentContainer.stringScanner.nextLine();
        System.out.println("Enter your password");
        String password=ComponentContainer.stringScanner.nextLine();
        profileService.login(login, password);
    }


    public void after() {
        System.out.println("After world");
    }
}
