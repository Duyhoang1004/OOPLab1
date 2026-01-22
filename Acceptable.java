/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tools;

/**
 *
 * @author DuyHoang
 */
public interface Acceptable {
    String CUSTOMER_ID_VALID ="^[CKG]\\d{4}$";
    
    String NAME_VALID = "^.{2,25}$";
    
    String DOUBLE_VALID = "^\\d+(\\.\\d+)?$";
    
    String INTEGER_VALID = "^\\d+$";
    
    String PHONE_VALID = "^\\d{10}$";
    
    String VIETTEL_VALID = "^(086|096|097|098|032|033|034|035|036|037|038|039)\\d{7}$";
    String VNPT_VALID = "^(088|091|094|081|082|083|084|085)\\d{7}$";
    
    String EMAIL_VALID = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    String MENU_ID_VALID = "^PW\\d{3}$";
    String POSITIVE_INTEGER = "^[1-9]\\d*$";
    
    static boolean isValid(String data, String pattern){
        return data!=null && data.matches(pattern);
    }
} 
