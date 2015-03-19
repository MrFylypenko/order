package com.bonvio.controller;

import com.bonvio.model.item.Item;

import com.bonvio.service.item.ItemService;
import com.bonvio.service.order.CommonOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        {"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml",
                "file:src/main/webapp/WEB-INF/spring/data.xml",
                "file:src/main/webapp/WEB-INF/spring/root-context.xml",
                "file:src/main/webapp/WEB-INF/spring/spring-security.xml"})

public class AppTests {

    /*@Autowired
    CommonOrderService commonOrderService;*/

    @Autowired
    CommonOrderService commonOrderService;


    @Autowired
    ItemService itemService;


    @Test
    public void simple() throws Exception {

        Locale.setDefault(Locale.ENGLISH);

        try {

            File fileDir = new File("c:\\items.tnt");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir)));  //, "UTF8"
            String str = null;

            int i = 0;
            List<Item> items = new ArrayList<Item>();

            System.out.println("hello fileDir");

            while ((str = in.readLine()) != null) {
                String[] array = str.split(";");  //str.split("\\|");
                Item item = new Item();
                item.setCategory(array[0]);
                item.setName(array[1]);

                double warehouse = Double.parseDouble(array[3].replace(",", "."));

                item.setWarehouse(warehouse);
                item.setMeasure(array[2]);

                items.add(item);
                //itemDao.addItem(item);

                System.out.println(item);

                itemService.createItem(item);


                i++;
            }





            System.out.println("Всего items="+ items.size());
            in.close();

            System.out.println("i=" + i);

            System.out.println("Готово");

        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }








       /* ItemCommonOrder itemCommonOrder = commonOrderService.getItemCommonOrderById(68);

        System.out.println(itemCommonOrder);

        itemCommonOrder.setDeferred(true);*/



       /* CommonOrder commonOrder = new CommonOrder();
        commonOrder.setCustomer("Test some object");

        ItemCommonOrder itemCommonOrder = new ItemCommonOrder();
        itemCommonOrder.setTitle("Test one object");

        ItemCommonOrder itemCommonOrder1 = new ItemCommonOrder();
        itemCommonOrder1.setTitle("inner object");


        List<ItemCommonOrder> itemCommonOrders2 = new ArrayList<ItemCommonOrder>();
        itemCommonOrders2.add(itemCommonOrder1);

        itemCommonOrder.setComponents(itemCommonOrders2);*/

        //itemCommonOrder.setItemCommonOrder(itemCommonOrder1);


/*
        List<ItemCommonOrder> itemCommonOrders = new ArrayList<ItemCommonOrder>();
        itemCommonOrders.add(itemCommonOrder);


        commonOrder.setComponents(itemCommonOrders);

        commonOrderService.saveCommonOrder(commonOrder);

        System.out.println(commonOrder);*/


    }
}
