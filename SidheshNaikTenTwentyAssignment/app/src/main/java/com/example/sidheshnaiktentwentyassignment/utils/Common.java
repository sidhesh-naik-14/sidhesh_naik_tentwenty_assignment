package com.example.sidheshnaiktentwentyassignment.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    @SuppressLint("SimpleDateFormat")
    public static String getExpectedDateFormat(String oldFormat, String expectedFormat,String dateString)  {
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        SimpleDateFormat output = new SimpleDateFormat(expectedFormat);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        return output.format(date);
    }
}
