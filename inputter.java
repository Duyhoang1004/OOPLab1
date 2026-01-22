/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author DuyHoang
 */
public class inputter {
    private Scanner sc;
      public inputter(Scanner sc) {
        this.sc = new Scanner(System.in);
    }

    public inputter() {
        this.sc = new Scanner(System.in);
    }
    
    public String getString(String msg){
        System.out.println(msg);
        return sc.nextLine().trim();
    }
     public int getInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(sc.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer! Please try again.");
            }
        }
    }
    
    public double getDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                double value = Double.parseDouble(sc.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number! Please try again.");
            }
        }
    }
    //input and check validation
    public String inputAndLoop(String message, String pattern) {
        String input;
        while (true) {
            input = getString(message);
            if (Acceptable.isValid(input, pattern)) {
                return input;
            }
            System.out.println("Invalid format! Please try again.");
        }
    }
    //input String to update information -> allow empty to keep old
     public String inputStringAllowEmpty(String message, String pattern) {
        String input = getString(message);
        if (input.isEmpty()) {
            return "";
        }
        if (Acceptable.isValid(input, pattern)) {
            return input;
        }
        System.out.println("Invalid format! Keeping old value.");
        return "";
    }
    //positive int 
    public int getPositiveInt(String message) {
        while (true) {
            String input = getString(message);
            if (Acceptable.isValid(input, Acceptable.POSITIVE_INTEGER)) {
                return Integer.parseInt(input);
            }
            System.out.println("Please enter a positive integer!");
        }
    }
    // empty to keep old
    public int getPositiveIntAllowEmpty(String message) {
        String input = getString(message);
        if (input.isEmpty()) {
            return -1;
        }
        if (Acceptable.isValid(input, Acceptable.POSITIVE_INTEGER)) {
            return Integer.parseInt(input);
        }
        System.out.println("Invalid format! Keeping old value.");
        return -1;
    }
    //date
    public Date getDate(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        while (true) {
            try {
                String input = getString(message);
                Date date = sdf.parse(input);
                Date today = new Date();
                if (date.after(today)) {
                    return date;
                }
                System.out.println("Event date must be in the future!");
            } catch (ParseException e) {
                System.out.println("Invalid date format! Please use dd/MM/yyyy.");
            }
        }
    }
    //empty to keep old
    public Date getDateAllowEmpty(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // set Lenient de kiem tra ngay
        String input = getString(message);
        if (input.isEmpty()) {
            return null;
        }
        try {
            Date date = sdf.parse(input);
            Date today = new Date();
            if (date.after(today)) {
                return date;
            }
            System.out.println("Event date must be in the future! Keeping old value.");
            return null;
        } catch (ParseException e) {
            System.out.println("Invalid date format! Keeping old value.");
            return null;
        }
    }
    
    public boolean getYesNo(String message) {
        while (true) {
            String input = getString(message + " (Y/N): ").toUpperCase();
            if (input.equals("Y")) return true;
            if (input.equals("N")) return false;
            System.out.println("Please enter Y or N!");
        }
    }
}
