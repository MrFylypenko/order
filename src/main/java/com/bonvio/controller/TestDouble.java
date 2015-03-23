package com.bonvio.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/**
 * Created by Ivan on 23.03.2015.
 */
public class TestDouble {

    public static void main (String ... a){

        double dennis = 0.00000008880000d;
        System.out.println(dennis);
        System.out.println(String.format("%.7f", dennis));
        System.out.println(String.format("%.9f", new BigDecimal(dennis)));
        System.out.println(String.format("%.19f", new BigDecimal(dennis)));

        double d=-0.00012;
        System.out.println(d+""); //This prints -1.2E-4

        double c=47.48000;

        BigDecimal b = new BigDecimal(d, MathContext.UNLIMITED);
        System.out.println(b.toString());


        // create a BigDecimal object
        BigDecimal bg;

        // create a Double Object
        double d1 = 00.008880000d;

        // assign the bigdecimal value of d to bg
        // static method is called using class name
        bg = BigDecimal.valueOf(d);

        String str = "The Double After Conversion to BigDecimal is " + bg;

        // print bg value
        System.out.println( str );



        double t = 1.15;

        System.out.println("t = " + t);


        BigDecimal t1 = new BigDecimal(t);

        System.out.println("t1 = " + t1);

        t1 = BigDecimal.valueOf(t);

        System.out.println("t1 = " + t1);



    }
}
