package org.example;

import org.example.config.SpringConfig;
import org.example.controller.MenuController;
import org.example.dbInit.DbInit;
import org.example.repository.CardRepo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;

public class Main{
    public static void main(String[] args)  {
        ApplicationContext context= new AnnotationConfigApplicationContext(SpringConfig.class);
//        DbInit dbInit=(DbInit) context.getBean("dbInit") ;
//        dbInit.createProfileTable();
//        dbInit.createCardTable();
//        dbInit.createTerminalTable();
//        dbInit.createTransactionTable();
//        dbInit.createAdmin();
//        dbInit.createCompanyCard();
        MenuController  menuController=(MenuController) context.getBean("menuController");
        menuController.start();












    }


}