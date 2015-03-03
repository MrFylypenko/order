package com.bonvio.controller;

import org.joda.time.DateTime;

import java.io.File;
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

        String path = "C:\\testfolder\\OpenDocumen.xls";

        File file = new File(path);
        System.out.println("file.exists = "+file.exists() + ", path = "  + path);



    }

}
