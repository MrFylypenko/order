package com.bonvio.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Ivan on 23.03.2015.
 */
public class TestDouble2 {

    public static void main(String... a) {

        //0.30000000000000004
        double d = Double.parseDouble("0.3000000000000004");

        double newDouble = new BigDecimal(d).setScale(5, RoundingMode.HALF_UP).doubleValue();

        System.out.println("newDouble" + newDouble);

        System.out.println("d = " + d);


    }

}
