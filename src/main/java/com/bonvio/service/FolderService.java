package com.bonvio.service;

import com.bonvio.model.admin.User;
import com.bonvio.model.order.CommonOrder;
import com.bonvio.service.admin.UserService;
import com.bonvio.service.order.CommonOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vano on 02.03.2015.
 */
@Service
public class FolderService {


    public FolderService() {
        System.out.println("ну создался сервис...");

        if(ExcelService.check == 0){
           /* Thread thread = new Thread(new ExcelService ());
            thread.start();*/
            ExcelService.check = 1;
        }





        // addingOrders();
    }





}
