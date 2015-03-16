package com.bonvio.controller;

/**
 * Created by Ivan on 16.03.2015.
 */
public class Test4 {



    public static void main (String ... a){

        String title = "База акриловая белая 20% блеск JO 20М800/BNC (тара 25,3 кг) москва";

        int start = title.indexOf("тара");
        int end = title.indexOf(")", start);

        String res = title.substring(start, end);
        System.out.println(res);

        String newString = new String();

for (int j = 0; j < res.length(); j++){

    char c = res.charAt(j);
    if ((c >= '0' & c <= '9') | c ==  ','  ) {

        if(c ==  ','){
            newString = newString + ".";
        } else{
            newString = newString + c;
        }


    }
}

        double rsD = new Double (newString);

        System.out.println("rsD =" + rsD);





    }



}
