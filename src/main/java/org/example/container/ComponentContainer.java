package org.example.container;

import org.example.controller.AdminController;
import org.example.controller.UserController;
import org.example.dto.ProfileDto;
import org.example.repository.*;
import org.example.service.*;

import java.util.Scanner;

public class ComponentContainer {
   public static Scanner stringScanner=new Scanner(System.in);
   public static Scanner intScanner=new Scanner(System.in);
   public static Scanner doubleScanner=new Scanner(System.in);
   public static ProfileDto profileDto=null;
   public static double fare=1400;
   //   public static ProfileService profileService= new ProfileService();
   //   public static ProfileRepo profileRepo= new ProfileRepo();
   //   public  static UserController userController= new UserController();
   //   public static UserService userService= new UserService();
   //   public static UserRepo userRepo= new UserRepo();
//   public static AdminController adminController= new AdminController();
//   public  static AdminService adminService= new AdminService();
//   public static AdminRepo adminRepo= new AdminRepo();
//   public static String defaultPhone=ComponentContainer.adminRepo.getAdminProfile().getPhone();
//   public static TransactionService transactionService = new TransactionService();
//   public static TransactionRepo transactionRepo= new TransactionRepo();
//   public static TerminalRepo terminalRepo= new TerminalRepo();
//   public static TerminalService terminalService= new TerminalService();
//   public static int adminCardNum=777;
}
