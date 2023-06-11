package org.example;

import org.example.controller.MenuController;
import org.example.dbInit.DBInit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context= new ClassPathXmlApplicationContext("spring-config.xml");
        DBInit dbInit=(DBInit) context.getBean("dbInit") ;
        dbInit.createTableProfile();
        dbInit.createTableCard();
        dbInit.createTableTerminal();
        dbInit.createTableTransaction();
        dbInit.createAdmin();
        dbInit.createCompanyCard();
        MenuController  menuController=(MenuController) context.getBean("menuController");
        menuController.start();
      







    }
}