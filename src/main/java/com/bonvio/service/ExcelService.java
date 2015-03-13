package com.bonvio.service;

import com.bonvio.model.admin.User;
import com.bonvio.model.order.CommonOrder;
import com.bonvio.model.order.ItemCommonOrder;
import com.bonvio.service.admin.UserService;
import com.bonvio.service.order.CommonOrderService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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

                    //List<CommonOrder> commonOrders = new ArrayList<CommonOrder>();
                    for (String stringFile : userFileNames) {

                        System.out.println(stringFile);

                        //commonOrders.add();
                        CommonOrder commonOrder = commonOrder(stringFile);
                        if (commonOrder.getCustomer().length() > 0)

                            System.out.println(commonOrder);

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

        CommonOrder commonOrder = new CommonOrder();

        FileInputStream file = null;


        try {

            System.out.println(" commonOrder inputFileName = " + inputFileName);

            File inputFile = new File(inputFileName);

            file = new FileInputStream(inputFile);


            HSSFWorkbook workbook = new HSSFWorkbook(file);

            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();

            //get number order
            //получения номера заказа и его описания
            int checkOrderDate = 0;
            while (rowIterator.hasNext()) {
                if (checkOrderDate != 0) {
                    break;
                }
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        if (cell.getStringCellValue().contains("Заказ")) {
                            commonOrder.setDate(cell.getStringCellValue());
                            checkOrderDate++;
                            break;
                        }
                    }
                }
            }


            //получение Заказчика
            int checkOrderCustomer = 0;
            boolean hasCustomer = false;
            while (rowIterator.hasNext()) {
                if (checkOrderCustomer != 0) {
                    break;
                }
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        System.out.println("zakaz" + cell.getStringCellValue());

                        if (hasCustomer) {
                            commonOrder.setCustomer(cell.getStringCellValue());
                            checkOrderCustomer++;
                            break;
                        }

                        if (cell.getStringCellValue().contains("Заказчик")) {
                            hasCustomer = true;
                        }
                    }
                }
            }


            //опредление нахождения позиций заказа
            int checkPositions = 0;

            int itemCode = 0;
            int itemTitle = 0;
            int itemQuantity = 0;


            while (rowIterator.hasNext()) {
                if (checkPositions != 0) {
                    break;
                }

                int numberCells = 0;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {

                        System.out.println("position = " + cell.getStringCellValue());

                        if (cell.getStringCellValue().contains("Код")) {
                            itemCode = numberCells;
                        }
                        if (cell.getStringCellValue().contains("Товары")) {
                            itemTitle = numberCells;
                        }
                        if (cell.getStringCellValue().contains("Количес")) {
                            itemQuantity = numberCells;
                            checkPositions++;
                            rowIterator.hasNext();
                            break;
                        }
                    }

                    numberCells++;
                }
            }


            int checkItems = 0;


           // System.out.println("itemCode =" + itemCode + " itemTitle = " + itemTitle + " itemQuantity = " + itemQuantity);


            //поиск и добавление товаров
            while (rowIterator.hasNext()) {
                if (checkItems != 0) {
                    break;
                }

                ItemCommonOrder itemCommonOrder = new ItemCommonOrder();

                int numberCells = 0;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                //System.out.println("поиск item");
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {

                        System.out.print("item = " + cell.getStringCellValue());

                        if (numberCells == itemCode) {
                            itemCommonOrder.setCode(cell.getStringCellValue());
                        }
                        if (numberCells == itemTitle) {
                            itemCommonOrder.setTitle(cell.getStringCellValue());
                        }
                        /*if (numberCells == itemQuantity) {
                            itemCommonOrder.setQuantity(cell.getStringCellValue());
                        }*/
                        if (numberCells > itemQuantity) {
                            itemCommonOrder.setQuantity(itemCommonOrder.getQuantity() + "  " + cell.getStringCellValue());
                            itemCommonOrder.setCategory("warehouse");
                            commonOrder.getItems().add(itemCommonOrder);
                            break;
                        }
                    }

                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC & (numberCells == itemQuantity)) {
                        itemCommonOrder.setQuantity(" " + cell.getNumericCellValue());

                    }



                   /* if ((cell.getCellType() != HSSFCell.CELL_TYPE_STRING) &   (numberCells == itemCode)){
                        checkItems++;
                    }*/

                    numberCells++;
                }
            }
            try {
                file.close();
            } catch (Exception e) {
                System.out.println("не удалось закрыть файловый поток");
                e.printStackTrace();
            }




            if (inputFile.delete()) {
                System.out.println(inputFile.getName() + " is deleted!");
            }

        } catch (Exception e) {

            try {
                file.close();
            } catch (Exception e1) {
                System.out.println("не удалось закрыть файловый поток");
                e1.printStackTrace();
            }

            System.out.println("не удалось сделать заказ...((");
            e.printStackTrace();
        }


        return commonOrder;
    }


}
