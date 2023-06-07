package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.container.ComponentContainer;
import org.example.controller.AdminController;
import org.example.controller.UserController;
import org.example.dto.ProfileDto;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.repository.ProfileRepo;
import org.example.util.CheckValidationUtil;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfileService {
    private ProfileRepo profileRepo;
    private UserController userController;
    private AdminController adminController;
    public void register(ProfileDto profileDto){
        // validate phoneNumber
        if (!CheckValidationUtil.isValidPhone(profileDto.getPhone())) {
            return;
        }
        //Check phone and user unique
        ProfileDto isExist1=profileRepo.getProfileByPhone(profileDto.getPhone());
        ProfileDto isExist2=profileRepo.getProfileByLogin(profileDto.getPhone());
        if (isExist1 !=null) {
            System.out.println("This phone is already registered");
            return;
        } else if ( isExist2 !=null)  {
            System.out.println("This login is already taken");
            return;
        }
               // set detail
               profileDto.setCreated_date(LocalDateTime.now());
               profileDto.setRole(ProfileRole.USER);
               profileDto.setStatus(ProfileStatus.ACTIVE);
               //save
               boolean result= profileRepo.addProfile(profileDto);
               if(result){
                   System.out.println("you have successfully registered");
                   System.out.println("Login to your account!");
               }else {
                   System.out.println("Something went wrong");
               }
        }
    public void login(String login, String password) {
       ProfileDto profileDto= profileRepo.login(login, password);
       if(profileDto==null){
           System.out.println("login or password is incorrect!");
           return;
       }else if(profileDto.getStatus().equals(ProfileStatus.BLOCKED)){
           System.out.println("your account is blocked!");
           return;
       }
           System.out.println("you have logged in successfully!");
           ComponentContainer.profileDto=profileDto;
           if(profileDto.getRole().equals(ProfileRole.USER)){
              userController.start();
               System.out.println("Welcome user");
           }else {
            adminController.start();
               System.out.println("Welcome admin");
           }
    }
}

