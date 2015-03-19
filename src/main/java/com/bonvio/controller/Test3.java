package com.bonvio.controller;

import com.bonvio.model.classes.RecipeTemplate;
import com.bonvio.model.item.Item;
import com.bonvio.model.order.CommonOrder;
import com.bonvio.service.ExcelService;
import com.bonvio.service.order.CommonOrderService;
import com.bonvio.service.order.CommonOrderServiceImpl;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Ivan on 05.03.2015.
 */
public class Test3 {



    public static void main(String[] args) throws IOException {

        ExcelService test = new ExcelService ();
        List<Item> recipeTemplates = test.getRecipes("C:\\testfolder\\test2\\Рецептуры эмали ПУ - 4.xls");
        System.out.println(recipeTemplates.size());

        for (int i = 0; i< 10; i++){
            System.out.println(recipeTemplates.get(i));

        }



        /*ExcelService test = new ExcelService ();
        CommonOrder commonOrder = test.commonOrder("C:\\testfolder\\Заказ 1340.xls");
        System.out.println("="+ commonOrder);
        CommonOrderService commonOrderService = new CommonOrderServiceImpl();
        commonOrderService.saveCommonOrder(commonOrder);*/


    }



}
