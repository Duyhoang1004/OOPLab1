/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Tien Dat
 */
public class Order implements Serializable {
    private String orderCode;
    private String customerId;
    private String menuId;
    private int numOfTable;
    private Date eventDate;
    private String generateOrderCode(){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        return sdf.format(now);
    }

    public Order(String customerId, String menuId, int numOfTable, Date eventDate) {
         this.orderCode = generateOrderCode();
        this.customerId = customerId;
        this.menuId = menuId;
        this.numOfTable = numOfTable;
        this.eventDate = eventDate;
    }

    public Order() {
        this.orderCode = generateOrderCode();
        this.customerId = "";
        this.menuId = "";
        this.numOfTable = 0;
        this.eventDate = new Date();
    }

    /**
     * @return the orderCode
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * @param orderCode the orderCode to set
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the menuId
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * @param menuId the menuId to set
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    /**
     * @return the numOfTable
     */
    public int getNumOfTable() {
        return numOfTable;
    }

    /**
     * @param numOfTable the numOfTable to set
     */
    public void setNumOfTable(int numOfTable) {
        this.numOfTable = numOfTable;
    }

    /**
     * @return the eventDate
     */
    public Date getEventDate() {
        return eventDate;
    }

    /**
     * @param eventDate the eventDate to set
     */
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
    
    
    
}
