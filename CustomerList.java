/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dto.Customer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import tools.inputter;

/**
 *
 * @author DuyHoang
 */
public class CustomerList extends ArrayList<Customer>{
    private boolean isSaved ;
    private inputter ip = new inputter();


    //add new customer
    public void addCustomer(Customer cus){
        this.add(cus);
    }
    public Customer searchById(String id){
        for(Customer c : this){
            if(c.getId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }
    
    public void saveToFile(String filename){
        try{
            File f = new File(filename);
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            for(Customer c : this){
                oos.writeObject(c);
            }
            oos.close();
            isSaved = true;
            System.out.println("Saved to file successfully");
        }catch(Exception e){
            System.out.println("Error in customer file: " + e.getMessage());
        }
    
    }
        public void readFromFile(String filename){
            try{
                FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
                
                while(fis.available() > 0){
                    Customer c = (Customer) ois.readObject();
                    this.add(c);
                }
                ois.close();
                this.isSaved = true;
            }catch(Exception e){
                System.out.println("Error in customer file : " + e.getMessage());
            }
            System.out.println("Customer list loaded successfully");
        }
        
        private String getFormattedName(String fullname){
            String[] subName = fullname.trim().split("\\s+");
            if(subName.length <= 1) return fullname;
            
            String firstName = subName[subName.length -1];
            
            StringBuilder lastName = new StringBuilder();
            for (int i = 0; i < subName.length - 1; i++) {
                lastName.append(subName[i]);
                if (i < subName.length - 2) {
                    lastName.append(" ");
                }
            }
            return firstName + "," + lastName;  
        }
        public List<Customer> filterByName(String name){
            List<Customer> result = new ArrayList<>();
            for(Customer c : this){
                if(c.getName().toUpperCase().contains(name.toUpperCase())){
                    result.add(c);
                }
            }
            return result;
        }
        public void SearchCustomerByName(){
            System.out.println("Search Customer by name:  ");
            String name = ip.getString("Enter customer name: ");
            
            List<Customer> result = filterByName(name);
            
            if(result.isEmpty()){
                System.out.println("No one matches the search criteria!");
            }
            else{
                result.sort((c1,c2) ->{
                   String f1 = getFormattedName(c1.getName());
                   String f2 = getFormattedName(c2.getName());
                   return f1.compareToIgnoreCase(f2);
                }); 
            }
            
            System.out.println("---------------------------------------------------------------------------");
            System.out.printf("%-10s | %-25s | %-15s | %-25s\n", "Code", "Customer Name", "Phone", "Email");
            System.out.println("----------------------------------------------------------------------------");
            
            for (Customer c : result) {
            System.out.printf("%-10s | %-25s | %-15s | %-25s\n", 
                c.getId(), 
                getFormattedName(c.getName()), 
                c.getPhone(), 
                c.getEmail());
        }
    }
        
        public void displayAll(){
             if (isEmpty()) {
            System.out.println("No data in the system.");
            return;
        }
        
        // Sort by name
        Collections.sort(this, new Comparator<Customer>() {
            @Override
            public int compare(Customer c1, Customer c2) {
                return c1.getName().compareToIgnoreCase(c2.getName());
            }
        });
        
        System.out.println("\nCustomers information:");
        System.out.println("------------------------------------------------------------------");
        System.out.println(String.format("%-10s | %-25s | %-12s | %-30s", 
                                        "Code", "Customer Name", "Phone", "Email"));
        System.out.println("------------------------------------------------------------------");
        for(Customer c : this){
        System.out.println(String.format("%-10s | %-25s | %-12s | %-13s",
                                            c.getId(),
                                            getFormattedName(c.getName()),
                                            c.getPhone(),
                                            c.getEmail()));
        }
        System.out.println("------------------------------------------------------------------");
        }
}
