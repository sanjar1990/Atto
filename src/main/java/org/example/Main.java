package org.example;

import org.example.config.SpringConfig;
import org.example.controller.MenuController;
import org.example.dbInit.DBInit;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;

public class Main{
    public static void main(String[] args)  {
        ApplicationContext context= new AnnotationConfigApplicationContext(SpringConfig.class);

//        DBInit dbInit=(DBInit) context.getBean("dbInit") ;
//        dbInit.createTableProfile();
//        dbInit.createTableCard();
//        dbInit.createTableTerminal();
//        dbInit.createTableTransaction();
//        dbInit.createAdmin();
//        dbInit.createCompanyCard();
        MenuController  menuController=(MenuController) context.getBean("menuController");
        menuController.start();
        System.out.println("Test");







    }


}