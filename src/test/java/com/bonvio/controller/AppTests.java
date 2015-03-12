package com.bonvio.controller;

import com.bonvio.model.order.CommonOrder;
import com.bonvio.model.order.ItemCommonOrder;
import com.bonvio.service.order.CommonOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    CommonOrderService commonOrderService;

    @Test
    public void simple() throws Exception {

        CommonOrder commonOrder = new CommonOrder();
        commonOrder.setCustomer("Test some object");

        ItemCommonOrder itemCommonOrder = new ItemCommonOrder();
        itemCommonOrder.setTitle("Test one object");

        ItemCommonOrder itemCommonOrder1 = new ItemCommonOrder();
        itemCommonOrder1.setTitle("inner object");


        List<ItemCommonOrder> itemCommonOrders2 = new ArrayList<ItemCommonOrder>();
        itemCommonOrders2.add(itemCommonOrder1);

        itemCommonOrder.setComponents(itemCommonOrders2);

        //itemCommonOrder.setItemCommonOrder(itemCommonOrder1);



        List<ItemCommonOrder> itemCommonOrders = new ArrayList<ItemCommonOrder>();
        itemCommonOrders.add(itemCommonOrder);


        commonOrder.setItems(itemCommonOrders);

        commonOrderService.saveCommonOrder(commonOrder);

        System.out.println(commonOrder);


    }
}
