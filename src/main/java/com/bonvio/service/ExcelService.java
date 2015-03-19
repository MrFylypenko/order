package com.bonvio.service;

import com.bonvio.dao.admin.SettingsDao;
import com.bonvio.model.admin.Settings;
import com.bonvio.model.admin.User;
import com.bonvio.model.classes.RecipeComponentTemplate;
import com.bonvio.model.classes.RecipeTemplate;
import com.bonvio.model.item.Component;
import com.bonvio.model.item.Item;
import com.bonvio.model.order.CommonOrder;
import com.bonvio.model.order.ItemCommonOrder;
import com.bonvio.service.admin.SettingsService;
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

    @Autowired
    UserService userService;

    @Autowired
    CommonOrderService commonOrderService;

    @Autowired
    SettingsService settingsDao;

    static int k = 0;

    public void runCheckingFolder() {

        if (k == 0) {
            k++;

            while (true) {

                try {
                    Thread.sleep(10000);

                    Settings settings = settingsDao.getSettings();

                    //System.out.println("settings.isUploadDefault()" + settings.isUploadDefault());

                    if (settings.isUploadDefault()) {

                        List<User> userList = userService.getAllUsers();
                        List<String> userFileNames = new ArrayList<String>();

                        for (User user : userList) {
                            String[] strings = listFile(user.getPath(), ".xls");
                            userFileNames.addAll(Arrays.asList(strings));
                        }

                        for (String stringFile : userFileNames) {
                            CommonOrder commonOrder = new CommonOrder();

                            try {
                                commonOrder = commonOrder(stringFile);
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("Не удалось распарсить файл");
                            }

                            if (commonOrder.getCustomer().length() > 0)

                                commonOrderService.saveCommonOrder(commonOrder);
                        }

                        System.out.println("делаю запрос № " + k++);
                    }

                } catch (InterruptedException e) {
                    System.out.println("Упс, ошибка");
                    e.printStackTrace();
                    //break;
                }
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

                            String[] strings = cell.getStringCellValue().split(" ");

                            if (strings.length > 3) {
                                try {
                                    int number = Integer.parseInt(strings[3]);
                                    commonOrder.setNumber(number);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
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
                            itemCommonOrder.setMeasure(cell.getStringCellValue());
                            //itemCommonOrder.setQuantity(itemCommonOrder.getQuantity() + "  " + cell.getStringCellValue());
                            itemCommonOrder.setCategory("original");
                            commonOrder.getComponents().add(itemCommonOrder);
                            break;
                        }
                    }

                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC & (numberCells == itemQuantity)) {
                        itemCommonOrder.setQuantity(cell.getNumericCellValue());

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


    public List<RecipeTemplate> getRecipeTemplate(String inputFileName) {

        List<RecipeTemplate> recipeTemplates = new ArrayList<RecipeTemplate>();

        FileInputStream file = null;

        try {

            System.out.println(" recipeTemplate inputFileName = " + inputFileName);

            File inputFile = new File(inputFileName);

            file = new FileInputStream(inputFile);


            HSSFWorkbook workbook = new HSSFWorkbook(file);

            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();

            // получение списка рецептов


            RecipeTemplate recipeTemplate = new RecipeTemplate();

            RecipeComponentTemplate recipeComponentTemplate = new RecipeComponentTemplate();


            while (rowIterator.hasNext()) {

                //System.out.println("выполняется метод2");
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                int indexName = 1;
                int indexPercent = 2;
                int indexQuantity = 3;

                String name = new String();
                String percent = new String();
                double quantity = 0;


                int indexCell = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    System.out.print(" indexCell=" + indexCell + " CellType=" + cell.getCellType() + "  ");

                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        System.out.print("cellString={" + cell.getStringCellValue() + "} ");

                        if (indexCell == indexName) {
                            name = cell.getStringCellValue();
                        }
                        if (indexCell == indexPercent) {
                            percent = cell.getStringCellValue();
                        }
                    }
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        System.out.println("cellNum={" + cell.getNumericCellValue() + "}");
                        if (indexCell == indexQuantity) {
                            quantity = cell.getNumericCellValue();
                        }
                    }
                    indexCell++;

                }


                System.out.println();

                if (percent.length() > 1 & name.length() > 1) {
                    recipeTemplate = new RecipeTemplate();
                    recipeTemplate.setName(name);
                    recipeTemplate.setPercent(percent);
                    recipeTemplate.setQuantity(quantity);
                }

                if (percent.length() == 0 & name.length() > 1) {
                    recipeComponentTemplate = new RecipeComponentTemplate();
                    recipeComponentTemplate.setName(name);
                    recipeComponentTemplate.setQuantity(quantity);
                    recipeTemplate.getComponents().add(recipeComponentTemplate);
                }

                if (percent.length() == 0 & name.length() == 0) {
                    recipeTemplates.add(recipeTemplate);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (Exception e) {
                System.out.println("не удалось закрыть файловый поток");
                e.printStackTrace();
            }
        }


        return recipeTemplates;
    }


    public List<Item> getRecipes(String inputFileName) {

        List<Item> recipeTemplates = new ArrayList<Item>();


        List<Item> items = new ArrayList<Item>();


        FileInputStream file = null;

        try {

            System.out.println(" recipeTemplate inputFileName = " + inputFileName);

            File inputFile = new File(inputFileName);

            file = new FileInputStream(inputFile);


            HSSFWorkbook workbook = new HSSFWorkbook(file);

            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();

            // получение списка рецептов


            RecipeTemplate recipeTemplate = new RecipeTemplate();

            RecipeComponentTemplate recipeComponentTemplate = new RecipeComponentTemplate();


            Item item = new Item();

            while (rowIterator.hasNext()) {


                //System.out.println("выполняется метод2");
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();


                String recipe = new String();
                //items



                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    String name = new String();
                    double quantity = 0.0;

                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        name = cell.getStringCellValue();
                        if(name.length() == 0){
                            break;
                        }
                        
                    }

                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        quantity = cell.getNumericCellValue();
                    }


                    if(quantity == 1.0){
                        item = new Item();
                        item.setName(name);
                        item.setQuantity(quantity);
                        item.setType("recipe");
                        items.add(item);
                    }

                    if(quantity != 1.0){
                        Item item2 = new Item();
                        item2.setName(name);
                        item2.setQuantity(quantity);
                        item2.setType("component");
                        Component component = new Component();
                        component.setItem(item2);
                        component.setParentItem(item);
                        component.setQuantity(quantity);
                        item.getComponents().add(component);
                    }

                }

                /*int indexName = 1;
                int indexPercent = 2;
                int indexQuantity = 3;

                String name = new String();
                String percent = new String();
                double quantity = 0;*/


              /*  int indexCell = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    System.out.print(" indexCell=" + indexCell + " CellType=" + cell.getCellType() + "  ");

                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        System.out.print("cellString={" + cell.getStringCellValue() + "} ");

                        if (indexCell == indexName) {
                            name = cell.getStringCellValue();
                        }
                        if (indexCell == indexPercent) {
                            percent = cell.getStringCellValue();
                        }
                    }
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        System.out.println("cellNum={" + cell.getNumericCellValue() + "}");
                        if (indexCell == indexQuantity) {
                            quantity = cell.getNumericCellValue();
                        }
                    }
                    indexCell++;
                }*/


              /*  System.out.println();

                if (percent.length() > 1 & name.length() > 1) {
                    recipeTemplate = new RecipeTemplate();
                    recipeTemplate.setName(name);
                    recipeTemplate.setPercent(percent);
                    recipeTemplate.setQuantity(quantity);
                }

                if (percent.length() == 0 & name.length() > 1) {
                    recipeComponentTemplate = new RecipeComponentTemplate();
                    recipeComponentTemplate.setName(name);
                    recipeComponentTemplate.setQuantity(quantity);
                    recipeTemplate.getComponents().add(recipeComponentTemplate);
                }

                if (percent.length() == 0 & name.length() == 0) {
                    //recipeTemplates.add(recipeTemplate);
                }*/

            }



            for(int i = 0; i < 50; i++){
                System.out.println(items.get(i));
            }



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (Exception e) {
                System.out.println("не удалось закрыть файловый поток");
                e.printStackTrace();
            }
        }


        return items;
    }

}
