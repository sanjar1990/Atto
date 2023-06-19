package org.example.container;

import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
public class ScannerComponent {
    private Scanner scanner;
    public ScannerComponent(){
        scanner=new Scanner(System.in);
    }

    public Scanner getScanner() {
        return scanner;
    }
}
