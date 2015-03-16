package com.bonvio.controller;

import com.bonvio.model.order.CommonOrder;
import com.bonvio.model.order.ItemCommonOrder;
import com.bonvio.service.ExcelService;


import com.bonvio.service.order.CommonOrderServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Ivan on 20.02.2015.
 */
public class Test {


    public static void main(String ar[]) {


      /*  try{

            InputStream input = new FileInputStream("C:\\testfolder\\Заказ 1454.xls");
            POIFSFileSystem fs = new POIFSFileSystem (input);


            //InputStream is1 = new FileInputStream("C:\\testfolder\\Заказ 1454.xls");
            //ce fichier permettra de récupérer les dates pour savoir si un extract à déjà été utilisé.

            HSSFWorkbook wb1 = new HSSFWorkbook(fs);

            HSSFSheet sheet1 = wb1.getSheetAt(0);
            HSSFRow row1 = null;
            HSSFCell cell1 = null;
            System.out.println("ON RECUPERE TOUTES LES DATES DU FICHIERS");


            HSSFRow row = sheet1.getRow(1);
            HSSFCell cell = row.getCell((short)0);

            System.out.println(cell.getStringCellValue());


        }catch (Exception e){e.printStackTrace();}

*/


        ExcelService excelService = new ExcelService();


        CommonOrder commonOrder = excelService.commonOrder("C:\\testfolder\\Заказ 1453 - копия.xls");



        CommonOrderServiceImpl commonOrderService = new CommonOrderServiceImpl();

        commonOrderService.saveCommonOrder(commonOrder);

                //commonOrder("C:\\testfolder\\Заказ 1465.xls");
        System.out.println(commonOrder);


    }


    public static CommonOrder commonOrder(String inputFileName) {

        CommonOrder commonOrder = new CommonOrder();

        try {

            System.out.println(" commonOrder inputFileName = " + inputFileName);

            File inputFile = new File(inputFileName);

            //FileInputStream file = new FileInputStream(inputFile);
            InputStream file = new FileInputStream(inputFileName);

            HSSFWorkbook workbook = new HSSFWorkbook(file);

            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();

            System.out.println("work");


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

                        System.out.println("=" + cell.getStringCellValue());

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


            //поиск и добавление товаров
            while (rowIterator.hasNext()) {
                if (checkItems != 0) {
                    break;
                }

                ItemCommonOrder itemCommonOrder = new ItemCommonOrder();

                int numberCells = 0;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {

                        if (numberCells == itemCode) {
                            itemCommonOrder.setCode(cell.getStringCellValue());
                        }
                        if (numberCells == itemTitle) {
                            itemCommonOrder.setTitle(cell.getStringCellValue());
                        }
                        if (numberCells == itemQuantity) {
                            //itemCommonOrder.setQuantity(cell.getStringCellValue());
                        }
                        if (numberCells > itemQuantity) {
                            //itemCommonOrder.setQuantity(itemCommonOrder.getQuantity() + "  " + cell.getStringCellValue());
                            commonOrder.getComponents().add(itemCommonOrder);
                            break;
                        }
                    }

                    if ((cell.getCellType() != HSSFCell.CELL_TYPE_STRING) & (numberCells == itemCode)) {
                        checkItems++;
                    }

                    numberCells++;
                }
            }

            try {
                file.close();
            } catch (Exception e) {
                System.out.println("не удалось закрыть файловый поток");
                e.printStackTrace();
            }


            /*if (inputFile.delete()) {
                System.out.println(inputFile.getName() + " is deleted!");
            }*/

        } catch (Exception e) {
            System.out.println("не удалось сделать заказ...((");
            e.printStackTrace();
        }


        return commonOrder;
    }

}
