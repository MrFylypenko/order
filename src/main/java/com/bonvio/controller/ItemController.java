package com.bonvio.controller;


import com.bonvio.model.item.Component;
import com.bonvio.model.item.Item;
import com.bonvio.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ivan on 27.02.2015.
 */

@Controller
@RequestMapping(value = "item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/getallitems", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getAllItems() {
        List<Item> res = itemService.getAllItems();
        return res;
    }

    @RequestMapping(value = "/additem", method = RequestMethod.POST)
    @ResponseBody
    public Item additem(@RequestBody Item item) {
        System.out.println(item);
        itemService.createItem(item);
        return item;
    }

    @RequestMapping(value = "/updateitem", method = RequestMethod.POST)
    @ResponseBody
    public Item updateItem(@RequestBody Item item) {


        //TODO обновлять вместе с содержимыми компонентами
        itemService.updateItem(item);
        return item;
    }

    @RequestMapping(value = "/getitemsbyexpression/{expression}", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getItemsByExpression(@PathVariable ("expression") String expression) {
        return itemService.getItemsByExpression(expression);
    }


    //TODO сделать удаление только рецепта   deleteitem/{id}

    @RequestMapping(value = "/addcomponent/{iditem}", method = RequestMethod.POST)
    @ResponseBody
    public Component addComponent(@PathVariable ("iditem") int iditem, @RequestBody Component component) {
        itemService.addComponent(component, iditem);
        return component;
    }

    @RequestMapping(value = "/updatecomponent", method = RequestMethod.POST)
    @ResponseBody
    public Component updateComponent(@RequestBody Component component) {
        itemService.updateComponent(component);
        return component;
    }

    @RequestMapping(value = "/removecomponent/{idcomponent}", method = RequestMethod.POST)
    @ResponseBody
    public String removeComponent(@PathVariable ("idcomponent") int idComponent) {
        itemService.removeComponent(idComponent);
        return "1";
    }







}


