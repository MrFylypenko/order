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

    @Autowired
    UserService userService;

    @Autowired
    ExcelService excelService;

    @Autowired
    CommonOrderService commonOrderService;

    int k = 0;


    void addingOrders() {

        try {
            while (true) {
                List<User> userList = userService.getAllUsers();
                List<String> userFiles = new ArrayList<String>();

                for (User user : userList) {
                    String[] strings = listFile(user.getPath(), ".xls");
                    userFiles.addAll(Arrays.asList(strings));
                }

                List<CommonOrder> commonOrders = new ArrayList<CommonOrder>();
                for (String file : userFiles) {
                    //commonOrders.add();
                    CommonOrder commonOrder = excelService.commonOrder(new File(file));
                    if(commonOrder != null)
                    commonOrderService.saveCommonOrder(commonOrder);
                }

                wait(1000);
                System.out.println("делаю запрос № " + k++);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public FolderService() {
        System.out.println("ну создался сервис...");
       // addingOrders();
    }

    /*private static  String FILE_DIR = "c:\\test\\est";
    private static  String FILE_TEXT_EXT = ".xls";

    public static void main(String args[]) {
        new FolderService().listFile(FILE_DIR, FILE_TEXT_EXT);
    }
*/

    public String[] listFile(String folder, String ext) {

        GenericExtFilter filter = new GenericExtFilter(ext);

        File dir = new File(folder);

        String[] list = new String[0];

        if (dir.isDirectory() == false) {
            System.out.println("Directory does not exists : " + folder);
            return list;
        }

        // list out all the file name and filter by the extension
        list = dir.list(filter);

        if (list.length == 0) {
            System.out.println("no files end with : " + ext);
            return list;
        }

        for (String file : list) {
            file = folder + file;
            System.out.println(file);
        }

        return list;
    }

    // inner class, generic extension filter
    public class GenericExtFilter implements FilenameFilter {

        private String ext;

        public GenericExtFilter(String ext) {
            this.ext = ext;
        }

        public boolean accept(File dir, String name) {
            return (name.endsWith(ext));
        }
    }


}
