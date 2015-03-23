package com.bonvio.controller;

import com.bonvio.dao.item.ComponentDao;
import com.bonvio.model.item.Component;
import com.bonvio.model.item.Item;

import com.bonvio.service.item.ItemService;
import com.bonvio.service.order.CommonOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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

public class AppTest2 {

    @Autowired
    ItemService itemService;


    @Autowired
    ComponentDao componentDao;

    @Test
    @Transactional
    @Rollback(false)
    public void simple() throws Exception {

       /* List<Component > components = componentDao.getAllComponents();

        System.out.println("components = " + components.size());

        for(int i = 0; i < components.size(); i ++){
            Component component = components.get(i);

            System.out.println(component.getQuantity());
            //double res = new BigDecimal(component.getQuantity()).setScale(5, RoundingMode.HALF_UP).doubleValue();


            //component.setQuantity(res);

        }*/













        List <Item> items = itemService.getAllRecipes();

        System.out.println(items.size());

        // для округления компонентов из 0.30000000000000004 в 0.3
       /* for (int i = 0; i < items.size();i++){
            Item item = items.get(i);

            double res = new BigDecimal(item.getQuantity()).setScale(5, RoundingMode.HALF_UP).doubleValue();
            item.setQuantity(res);
            System.out.println(item.getQuantity());
        }*/

        /*for (int i = 0; i < items.size();i++){
            Item item = items.get(i);

            for(int k = 0 ; k < item.getComponents().size(); k++){
                itemService.removeComponent(item.getComponents().get(k).getId());

            }


            itemService.removeRecipe((int)item.getId());
        }*/


        for (int i = 0; i < items.size();i++){

            Item item = items.get(i);

            BigDecimal bigDecimal = new BigDecimal(BigInteger.ZERO);

            for(int k = 0 ; k < item.getComponents().size(); k++){
                Component component = item.getComponents().get(k);

                BigDecimal quantity = BigDecimal.valueOf(component.getQuantity());
                bigDecimal = bigDecimal.add(quantity);

            }
            System.out.println("bigDecimal = "+bigDecimal);

            double result = bigDecimal.doubleValue();

            item.setQuantity(result);

            itemService.updateRecipe(item);
        }
        System.out.println("Усё");
    }
}
