package com.accenture.modulosPago;

public class Utils {
    public static Boolean verifyNumber(String number){
        try {
            Double.parseDouble(number);
            return true;
        }catch (NumberFormatException e){
            e.getMessage();
            return false;
        }
    }
}
