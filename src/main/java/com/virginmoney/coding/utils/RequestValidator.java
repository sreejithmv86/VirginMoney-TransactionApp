package com.virginmoney.coding.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class RequestValidator {

    private static boolean isValidDatePattern(String dateStr, String pattern) {
        DateFormat formatter1 = new SimpleDateFormat(pattern,new Locale("en","IN"));
        formatter1.setLenient(false);
        try {
            formatter1.parse(dateStr);
            return true;
        } catch (ParseException e) {
            System.out.println("The provided date input : " + dateStr +
                    " does not match the expected pattern : " + pattern);
            return false;
        }
    }

    public static boolean isValidYearMonthPattern(String yearMonth) {
        Pattern pattern = Pattern.compile("\\d{4}[-]\\d{1,2}");
        if(pattern.matcher(yearMonth).matches()){
           return isValidDatePattern(yearMonth,"yyyy-MM");
        }
        return false;
    }

    public static boolean isValidYearPattern(String year) {
        Pattern pattern = Pattern.compile("\\d{4}");
        if(pattern.matcher(year).matches()){
            return isValidDatePattern(year,"yyyy");
        }
        return false;
    }
}
