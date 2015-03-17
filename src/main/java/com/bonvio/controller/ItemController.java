package com.bonvio.controller;

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

    @RequestMapping(value = "/createitem", method = RequestMethod.POST)
    @ResponseBody
    public Item addItem(@RequestBody Item item) {
        itemService.createItem(item);
        return item;
    }

    @RequestMapping(value = "/updateitem", method = RequestMethod.POST)
    @ResponseBody
    public Item updateItem(@RequestBody Item item) {
        itemService.updateItem(item);
        return item;
    }

    @RequestMapping(value = "/getitemsbyexpression/{expression}", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getItemsByExpression(@PathVariable ("expression") String expression) {
        return itemService.getItemsByExpression(expression);
    }

}


