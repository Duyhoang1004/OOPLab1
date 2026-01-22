/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dto.Customer;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import services.CustomerList;
import services.OrderList;
import services.SetMenuList;
import tools.Acceptable;
import tools.inputter;

/**
 *
 * @author DuyHoang
 */
public class app {
    CustomerList cusList = new CustomerList();
    Scanner sc = new Scanner(System.in);
    inputter ip = new inputter();
    SetMenuList menuList = new SetMenuList();
    OrderList orderlist = new OrderList();
    
    
public app(){
    //load datafromfile
   
    cusList.readFromFile("Customer.dat");
    orderlist.loadFromFile("feast_order_service.dat");
    menuList.readFromFile("FeastMenu.csv");
     
}
    
    public static void main(String[] args) throws IOException{
        app a = new app();
        Scanner sc = new Scanner(System.in);
         int option = 0;
         
    try{
        boolean stop = true;
        do{
                System.out.println("Enter number to choose a service below:");
                System.out.println("1.Register customer");
                System.out.println("2.Update Customer information");
                System.out.println("3.Search for customer information by name");
                System.out.println("4.Display feast menu");
                System.out.println("5.Place a feast menu");
                System.out.println("6.Update order information");
                System.out.println("7.Save data to file");
                System.out.println("8.Display Customer or Order List");
                System.out.println("Any other number to quit the menu");
                
                System.out.print("Enter a number: ");
               option = sc.nextInt();
                sc.nextLine();
                switch(option){
                    case 1:
                        a.register();
                        break;
                    case 2:
                        a.updateCusInformation();
                        break;
                    case 3:
                        a.searchByCusName();
                        break;
                    case 4: 
                       a.displayMenu();
                       break;
                    case 5:
                        a.placeFeastMenu();
                        break;
                    case 6:
                        a.UpdateOrderInfo();
                    case 7:
                        a.SaveDataToFile();
                        break;
                    case 8:
                        a.displayCusAndOrder();
                        break;
                    default:
                        System.out.println("Exit programm");
                        System.exit(0);
                }
        }while(stop);
        }catch(Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
    }
     }
    public void register(){
        String id,name,phone,email;
        String choice = null;
        
        do{
            //Enter code
            while(true){
            System.out.println("Code must begin with C, K or G and follow by 4 digits");
            System.out.print("Enter customer code: ");
            id = sc.nextLine().trim().toUpperCase();
            if(Acceptable.isValid(id, Acceptable.CUSTOMER_ID_VALID)){
                if(cusList.searchById(id) == null) break;
                else System.out.println("Code already existed");
            }
            System.out.println("Invalid code");
            }
            
            //Nhap ten
            while(true){
            System.out.print("Enter cusomter name: ");
             name = sc.nextLine().toUpperCase();
            if(Acceptable.isValid(name, Acceptable.NAME_VALID)) {
                break;}
            else System.out.println("Name must be 2-25 length");
            }

            //Nhap SDT
            while(true){
                System.out.print("Enter customer phone number:  ");
                phone = sc.nextLine().trim();
                if(Acceptable.isValid(phone, Acceptable.PHONE_VALID)) break;
                else System.out.println("Invalid phone number pattern");
            }

            while(true){
                System.out.print("Enter customer email: ");
                email = sc.nextLine().trim();
                if(Acceptable.isValid(email, Acceptable.EMAIL_VALID)) break;
                else System.out.println("Invalid email");
            }
             ///tao object moi va add
             Customer validCus = new Customer(id,name,phone,email);
             cusList.addCustomer(validCus);
             System.out.println("Registered succesfully!");

             //tiep tuc dang ki hoac ve menu
             System.out.println("Do you want to continue on registering new customer [Y/N]: ");
             choice = sc.nextLine().toUpperCase();
        }while(choice.equals("Y"));
    }
    public void updateCusInformation(){
        String code,name,phone,email;
        System.out.print("Enter customer code to update: ");
        code = sc.nextLine().trim().toUpperCase();
        
        Customer cus = cusList.searchById(code);
        if(cus == null){
            System.out.println("This customer does not exist.");
            return;
        }
        
        System.out.println("Current information");
        System.out.println(cus);
        System.out.println("Leave blank to keep the old information");
        
        //change name
        
        name = ip.inputStringAllowEmpty("Enter new name : ", Acceptable.NAME_VALID);
        if(!name.isEmpty()){
            cus.setName(name);
        }
        
        //change phonenumber
        phone = ip.inputStringAllowEmpty("Enter new phone number : ", Acceptable.PHONE_VALID);
        if(!phone.isEmpty()){
            cus.setPhone(phone);
        }
        
        //change email
        email = ip.inputStringAllowEmpty("Enter new email: ", Acceptable.EMAIL_VALID);
        if(!email.isEmpty()){
            cus.setEmail(email);
        }
        System.out.println("Update successfully");
    }
    public void searchByCusName(){
        cusList.SearchCustomerByName();
    }
    public void SaveDataToFile(){
        System.out.println("Save data to file");
        System.out.println("1.Save customer list");
        System.out.println("2.Save order list");
        System.out.println("3.Save both");
        inputter ip = new inputter();
        int choose = ip.getInt("Enter a number: ");
        
        
        switch(choose){
            case 1:
                this.cusList.saveToFile("Customer.dat");
                break;
            case 2: 
                this.orderlist.saveToFile("feast_order_service.dat");
                break;
            case 3:
                cusList.saveToFile("Customer.dat");
                orderlist.saveToFile("feast_order_service.dat");
                break;
            default:
                System.out.println("Invalid option");
        }
    }
    public void displayMenu(){
        menuList.displayAll();
    }
    public void placeFeastMenu(){
        orderlist.placeOrder(cusList, menuList);
    }
    public void UpdateOrderInfo(){
        orderlist.updateOrderInformation( cusList,menuList);
    }
    public void displayCusAndOrder(){
        System.out.println("1.Display list of Customer");
        System.out.println("2.Display list of order");
        int choice = ip.getInt("Enter option: ");
        
        switch(choice){
            case 1:
                cusList.displayAll();
                break;
            case 2:
                orderlist.displayAll(cusList,menuList);
                break;
            default:
                System.out.println("Invalid option");
        }
                
    }
}
