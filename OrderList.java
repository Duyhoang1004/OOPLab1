/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dto.Customer;
import dto.Order;
import dto.SetMenu;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import tools.inputter;

/**
 *
 * @author Tien Dat
 */
public class OrderList extends ArrayList<Order> {
    private inputter ip;
    private boolean isSaved;
    
    public OrderList(){
        this.ip = new inputter();
        this.isSaved = true;
    }
    public void saveToFile(String filename){
        try{
            FileOutputStream f = new FileOutputStream(filename);
            ObjectOutputStream fo = new ObjectOutputStream(f);
            
            for(Order o : this){
                fo.writeObject(o);
            }
            fo.close();
            f.close();
            
            isSaved = true;
            System.out.println("Orders are saved successfully");
        }catch(Exception e){
            System.out.println("Error order file: "+ e.getMessage());
        }
    }
    public void loadFromFile(String filename){
        try{
             FileInputStream f = new FileInputStream(filename);
             ObjectInputStream fi = new ObjectInputStream(f);
             
             while(f.available() > 0){
                 Order o = (Order) fi.readObject();
                 add(o);
             }
             fi.close();
             f.close();
             isSaved = true;
             System.out.println("Order list loaded successfully");
        }
        catch(Exception e){
            System.out.println("Error order file: "+ e.getMessage());
        }
    }
    public void placeOrder(CustomerList customerlist, SetMenuList setmenulist){
        if(customerlist.isEmpty()){
            System.out.println("There is no customer registerd");
        }
        if(setmenulist.isEmpty()){
            System.out.println("No set menu is available");
        }
        
        String customerId;
        Customer customer;
        
        while(true){
            customerId = ip.getString("Enter customer ID: ").toUpperCase();
            customer = customerlist.searchById(customerId);
            if(customerId != null){
                break;
            }
            System.out.println("Customer Id does not exist, enter an a valid ID!");
        }
        String menuId;
        SetMenu setmenu;
        
        setmenulist.displayAll();
        while(true){
            menuId = ip.getString("Enter a menu id: ").toUpperCase();
            setmenu = setmenulist.findByCode(menuId);
            if(setmenu != null) break;
            System.out.println("SetMenu code does not exist!");
        }
        int numberOfTable = ip.getInt("Enter number of tables: ");
        Date eventDate = ip.getDate("Enter the event date(dd/MM/yyyy): ");
        Order newOrder = new Order(customerId,menuId,numberOfTable,eventDate);  
        
        if(this.contains(newOrder)) {
            System.out.println("Duplicated data!!");
            return;
        }
        this.add(newOrder);
        isSaved = false;
        disPlayOrderDetail(newOrder,customer,setmenu);
    }
    public String formatCurrency(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount) + " Vnd";
    }
    public void disPlayOrderDetail(Order order,Customer customer, SetMenu setmenu){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        double totalPrice = setmenu.getPrice() * order.getNumOfTable(); 
        System.out.println("--------------------------------------------");
        System.out.println("Customer order infomation" +"[" +"Order iD: " + order.getOrderCode() + "]");
        System.out.println("--------------------------------------------");
        System.out.println("Code: " + customer.getId());
        System.out.println("Customer name: " + customer.getName());
        System.out.println("Phone number: " + customer.getPhone());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("---------------------------------------------");
        
        System.out.println("Code of Set Menu: " + setmenu.getCode());
        System.out.println("Set Menu name: " + setmenu.getName());
        System.out.println("Event date:" + sdf.format(order.getEventDate()));
        System.out.println("Number of tables: "+ order.getNumOfTable());
        System.out.println("Price: "+ formatCurrency(setmenu.getPrice()));
        System.out.println("Ingredient: ");
        
        String[] temp = setmenu.getIngredient().split("#");
            for(String a : temp){
                System.out.println(a);
            }
        System.out.println("------------------------------------------------");
        System.out.println(String.format("Total cost: %s ",formatCurrency(totalPrice)));
        System.out.println("------------------------------------------------");
    }
    public Order searchOrderById(String id){
        for(Order o : this){
            if(o.getOrderCode().equalsIgnoreCase(id)) return o;
        }
        return null;
    }
    
    public void updateOrderInformation(CustomerList customerlist, SetMenuList setmenulist ){
        System.out.println("Update order information");
        String orderCode = ip.getString("Please enter order code:");
        Order order = searchOrderById(orderCode);
        Customer currentCustomer = customerlist.searchById(order.getCustomerId());
        SetMenu currentMenu = setmenulist.findByCode(order.getMenuId());
        
        if(order == null){
            System.out.println("There is no order match with entered code!");
            return;
        }
        //check if the event day has passed
        Date today = new Date();
        if(order.getEventDate().before(today)){
            System.out.println("The event date already passed");
            return;
        }
        System.out.println("Current order information");
        disPlayOrderDetail(order, currentCustomer, currentMenu);
        System.out.println("Blank if you want to keep the old information");
        
        //update MenuId
        String menuCode = ip.getString("Enter new menu code: ");
        if(!menuCode.isEmpty()){
            SetMenu menu = setmenulist.findByCode(menuCode);
            if(menu != null){
                order.setMenuId(menuCode);
            }
            else{
                System.out.println("Invalid menu code!");
            }
        }
        //numner of table
        int numOfTables = ip.getPositiveIntAllowEmpty("Enter new number of tables: ");
        if(numOfTables > 0){
            order.setNumOfTable(numOfTables);
        }
        
        //eventdate
        Date newDate = ip.getDateAllowEmpty("Enter new event date: ");
        if(newDate != null){
            order.setEventDate(newDate);
        }
        
        //done
        isSaved = false;
        System.out.println("Order information updated successfully");
    }
    
    public void displayAll(CustomerList customerlist, SetMenuList setmenulist){
        if(this.isEmpty()){
            System.out.println("No data saved in system");
            return;
        }
        Collections.sort(this, new Comparator<Order>(){
            public int compare(Order o1, Order o2){
                return o1.getEventDate().compareTo(o2.getEventDate());
            }
    });
        
        System.out.println("---------------------------------------------------");
        System.out.println(String.format("%-15s | %-12s | %-10s | %-10s | %12s | %6s | %15s", 
                                        "ID", "Event date", "Customer", "Menu", "Price", "Tables", "Cost"));
        System.out.println("-------------------------------------------------------------------------");
        
        for (Order o : this) {
            SetMenu menu = setmenulist.findByCode(o.getMenuId());
            double price = menu != null ? menu.getPrice() : 0;
            double totalCost = price * o.getNumOfTable();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            System.out.println(String.format("%-15s | %-12s | %-10s | %-10s | %12s | %6d | %15s",
                                            o.getOrderCode(),
                                            sdf.format(o.getEventDate()),
                                            o.getCustomerId(),
                                            o.getMenuId(),
                                            formatCurrency(price),
                                            o.getNumOfTable(),
                                            formatCurrency(totalCost)));
        }
        System.out.println("-------------------------------------------------------------------------");
    }
}
