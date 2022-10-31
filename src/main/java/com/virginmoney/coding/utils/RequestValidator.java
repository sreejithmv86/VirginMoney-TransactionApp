package com.virginmoney.coding.utils;

import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestValidator {

    public static boolean isValidDatePattern(String dateStr, String pattern) {
        DateFormat formatter1 = new SimpleDateFormat(pattern,new Locale("en","IN"));
        formatter1.setLenient(false);
        try {
            formatter1.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("The provided date input : " + dateStr +
                    " does not match the expected pattern : " + pattern);
            return false;
        }
        return true;
    }

    public static boolean isValidYearMonthPattern(String yearMonth) {
        Pattern pattern = Pattern.compile("\\d{4}[-]\\d{1,2}");
        return pattern.matcher(yearMonth).matches();
    }

    public static boolean isValidYearPattern(String year) {
        Pattern pattern = Pattern.compile("\\d{4}");
        return pattern.matcher(year).matches();
    }
}
