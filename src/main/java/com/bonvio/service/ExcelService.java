package com.bonvio.service;

import com.bonvio.model.order.CommonOrder;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Vano on 02.03.2015.
 */
@Service
public class ExcelService {

    public CommonOrder commonOrder(File inputFile) {

        CommonOrder commonOrder = null;

        try {
            FileInputStream file = new FileInputStream(inputFile);

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

                int i = 0;

                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    commonOrder.setCustomer(cell.getStringCellValue());
                    cell = cellIterator.next();
                    commonOrder.setDate(cell.getStringCellValue());
                    cell = cellIterator.next();
                    commonOrder.setNumber((int) cell.getNumericCellValue());
                    cell = cellIterator.next();
                    commonOrder.setPriority((int) cell.getNumericCellValue());

                    i++;
                }

            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return commonOrder;
    }


}
