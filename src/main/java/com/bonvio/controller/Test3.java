package com.bonvio.controller;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ivan on 05.03.2015.
 */
public class Test3 {

    private String inputFile;

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void read() throws IOException {
        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet = w.getSheet(0);
            // Loop over first 10 column and lines

            for (int j = 0; j < sheet.getColumns(); j++) {
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell cell = sheet.getCell(j, i);
                    CellType type = cell.getType();
                    if (type == CellType.LABEL) {
                        System.out.println("I got a label "
                                + cell.getContents());
                    }

                    if (type == CellType.NUMBER) {
                        System.out.println("I got a number "
                                + cell.getContents());
                    }

                }
            }
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Test3 test = new Test3();
        test.setInputFile("C:\\testfolder\\Заказ 1463.xls");
        test.read();
    }



}