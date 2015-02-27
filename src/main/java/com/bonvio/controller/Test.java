package com.bonvio.controller;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Ivan on 20.02.2015.
 */
public class Test {



    public static void main(String ar []) {




        Locale.setDefault(Locale.US);

        DateTime dt = new DateTime();
        Date d = new Date();



        System.out.println("" + d.getTime() + " \n " + dt.getMillis() + "    " + TimeZone.getDefault());
        System.out.println("" + d + "  \n" + dt + "    " );

       /* for(int i = 0; i < TimeZone.getAvailableIDs().length; i++){
            System.out.println(""+TimeZone.getAvailableIDs()[i] );
        }*/



        DateFormat df = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
        df.setTimeZone(TimeZone.getTimeZone("EAT"));
        System.out.println(df.format(d));


    }

}
