package com.bonvio.controller;

import com.bonvio.model.order.CommonOrder;
import com.bonvio.model.order.ItemCommonOrder;
import com.bonvio.service.order.CommonOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ivan on 27.02.2015.
 */
@Controller
@RequestMapping(value = "assistant")
public class AssistantController {

    @Autowired
    CommonOrderService commonOrderService;

    @RequestMapping(value = "/getallcommonorders", method = RequestMethod.GET)
    @ResponseBody
    public List<CommonOrder> getAllCommonOrders() {
        return commonOrderService.getAllCommonOrders();
    }

    @RequestMapping(value = "/getcommonorderbyid/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonOrder getCommonOrderById(@PathVariable("id") int id) {
        return commonOrderService.getCommonOrderById(id);
    }

    @RequestMapping(value = "/updatecommonorder", method = RequestMethod.POST)
    @ResponseBody
    public String updateCommonOrder(@RequestBody CommonOrder commonOrder) {
        commonOrderService.updateCommonOrder(commonOrder);
        return "1";
    }

    @RequestMapping(value = "/deletecommonorder", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCommonOrder(@RequestBody CommonOrder commonOrder) {
        commonOrderService.deleteCommonOrder(commonOrder);
        return "1";
    }

    @RequestMapping(value = "/getitemcommonorderbyid/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ItemCommonOrder getItemCommonOrderById(@PathVariable("id") int id) {
        return commonOrderService.getItemCommonOrderById(id);
    }

    @RequestMapping(value = "/getitemscommonordersbycommonorderid/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<ItemCommonOrder> getItemsCommonOrdersByCommonOrderId(@PathVariable("id") int id) {
        return commonOrderService.getItemsCommonOrdersByCommonOrderId(id);
    }


    @RequestMapping(value = "/updateitemcommonorder", method = RequestMethod.POST)
    @ResponseBody
    public String updateItemCommonOrder(@RequestBody ItemCommonOrder itemCommonOrder) {
        System.out.println("itemCommonOrder=" + itemCommonOrder);
        commonOrderService.updateItemCommonOrder(itemCommonOrder, 1);
        return "1";
    }


}
