package com.bonvio.service;

import com.bonvio.model.admin.User;
import com.bonvio.model.order.CommonOrder;
import com.bonvio.service.admin.UserService;
import com.bonvio.service.order.CommonOrderService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Vano on 02.03.2015.
 */
@Service
public class ExcelService {

    public static int check = 0;

    @Autowired
    UserService userService;

    @Autowired
    CommonOrderService commonOrderService;

    static int k = 0;


    public void runCheckingFolder() {

        if (k == 0) {
            k++;

            while (true) {
                try {
                    Thread.sleep(10000);
                    List<User> userList = userService.getAllUsers();
                    List<String> userFileNames = new ArrayList<String>();

                    for (User user : userList) {
                        String[] strings = listFile(user.getPath(), ".xls");
                        userFileNames.addAll(Arrays.asList(strings));
                    }

                    List<CommonOrder> commonOrders = new ArrayList<CommonOrder>();
                    for (String stringFile : userFileNames) {

                        //commonOrders.add();
                        CommonOrder commonOrder = commonOrder(stringFile);
                        if (commonOrder != null)
                            commonOrderService.saveCommonOrder(commonOrder);
                    }

                    System.out.println("делаю запрос № " + k++);

                } catch (InterruptedException e) {
                    System.out.println("Упс, ошибка");
                    e.printStackTrace();
                    break;
                }

                //break;

            }
        }


    }


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

        for (int i = 0; i < list.length; i++) {
            list[i] = folder + "\\" + list[i];
        }

        System.out.println("Проверка выполнения кода!");

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


    public CommonOrder commonOrder(String inputFileName) {


        CommonOrder commonOrder = null;

        try {

            System.out.println(" commonOrder inputFileName = " + inputFileName);

            File inputFile = new File(inputFileName);

            FileInputStream file = new FileInputStream(inputFile);


            HSSFWorkbook workbook = new HSSFWorkbook(file);

            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                System.out.print(cellIterator.next().getStringCellValue() + "   ");
                System.out.print(cellIterator.next().getStringCellValue() + "   ");
                System.out.print("numeric = "+cellIterator.next().getNumericCellValue() + "   ");


            }









           /*
            //Create Workbook instance holding reference to .xlsx file
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();


                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    System.out.print("  "+cell.getStringCellValue());


                    commonOrder.setCustomer(cell.getStringCellValue());
                    cell = cellIterator.next();
                    commonOrder.setDate(cell.getStringCellValue());
                    cell = cellIterator.next();
                    System.out.println(commonOrder);
                    commonOrder.setNumber((int) cell.getNumericCellValue());
                    cell = cellIterator.next();
                    commonOrder.setPriority((int) cell.getNumericCellValue());
                    System.out.println(commonOrder);


                    //break;

                }

                //break;

            }
            file.close();*/

            if (inputFile.delete()) {
                System.out.println(inputFile.getName() + " is deleted!");
            }

        } catch (Exception e) {
            System.out.println("не удалось сделать заказ...((");
            e.printStackTrace();
        }


        return commonOrder;
    }


}
