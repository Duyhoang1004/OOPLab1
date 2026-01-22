/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dto.SetMenu;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author DuyHoang
 */
public class SetMenuList extends ArrayList<SetMenu> {
     public void readFromFile(String filename){
        FileReader f =null;
        BufferedReader bf = null;
        
        
        try {
            f = new FileReader(filename);
            bf = new BufferedReader(f);
            String s = bf.readLine();//doc dong dau tien(caption trong file)
            while(bf.ready()){
                s = bf.readLine();
                String[] arr=s.split(",");
                //arr=["PW001","Wedding party","375000",]
                if(arr.length==4){
                    SetMenu m = new SetMenu();
                    m.setCode(arr[0]);
                    m.setName(arr[1]);
                    m.setPrice(Double.parseDouble(arr[2].trim()));
                    m.setIngredient(arr[3]);
                    add(m);
                }
                
            }
        } catch (Exception e) {
            System.out.println("File Error");
        } finally {
            try {
                if(f!=null) f.close();
                if(bf!=null) bf.close();
            } catch (Exception e) {
                System.out.println("Close file error");
            }
        }
    }
     public String formatCurrency(double amount) {
         DecimalFormat formatter = new DecimalFormat("#,###");
         return formatter.format(amount) + " Vnd";
    }
    public void displayAll(){
        System.out.println("-----------------------------------------");
        System.out.println("List of Set Menus for ordering party:");
        System.out.println("-----------------------------------------");
        for(SetMenu s : this){
            System.out.println("Code: " + s.getCode());
            System.out.println("Name: "+ s.getName());
            System.out.println("Price: " + formatCurrency(s.getPrice()));
            System.out.println("Ingredient: ");
            String[] temp = s.getIngredient().split("#");
            for(String a : temp){
                System.out.println(a);
            }
            System.out.println("---------------------------------------");
        }
    }
    public SetMenu findByCode(String code){
        for(SetMenu s : this){
            if(s.getCode().equalsIgnoreCase(code)) return s;
        }
        return null;
    }
}
