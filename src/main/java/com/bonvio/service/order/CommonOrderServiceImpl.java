package com.bonvio.service.order;

import com.bonvio.dao.item.ItemDao;
import com.bonvio.dao.order.CommonOrderDao;
import com.bonvio.dao.order.ItemCommonOrderDao;
import com.bonvio.model.item.Component;
import com.bonvio.model.item.Item;
import com.bonvio.model.order.CommonOrder;
import com.bonvio.model.order.ItemCommonOrder;
import com.bonvio.service.ExcelService;
import com.bonvio.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 24.02.2015.
 */
@Service
public class CommonOrderServiceImpl implements CommonOrderService {

    @Autowired
    CommonOrderDao commonOrderDao;

    @Autowired
    ItemCommonOrderDao itemCommonOrderDao;

    @Autowired
    ExcelService excelService;

    @Autowired
    ItemDao itemDao;


    @Override
    public CommonOrder getCommonOrderById(int idCommonOrder) {
        return commonOrderDao.getCommonOrderById(idCommonOrder);
    }

    @Override
    public List<CommonOrder> getAllCommonOrders() {
        excelService.runCheckingFolder();
        //System.out.println("отдал заказы");
        return commonOrderDao.getAllCommonOrders();
    }


    public void checkOrder(CommonOrder commonOrder) {

        CommonOrder commonOrder1 = commonOrderDao.getCommonOrderByOrderNumber(commonOrder.getNumber());

        if (commonOrder1 != null) {
            List<ItemCommonOrder> itemCommonOrdersFromBase = new ArrayList<ItemCommonOrder>();

            for (int i = 0; i < commonOrder1.getComponents().size(); i++) {
                if (commonOrder1.getComponents().get(i).getCategory().equals("original")) {
                    itemCommonOrdersFromBase.add(commonOrder1.getComponents().get(i));
                }
            }

            //проверка изменилось ли количество в наименовании
            for (int i = 0; i < commonOrder.getComponents().size(); i++) {
                ItemCommonOrder newComponent = commonOrder.getComponents().get(i);

                for (int k = 0; k < itemCommonOrdersFromBase.size(); k++) {
                    ItemCommonOrder oldComponent = itemCommonOrdersFromBase.get(k);

                    if (newComponent.getTitle().equals(oldComponent.getTitle())) {

                        double newValue = newComponent.getQuantity();
                        double oldValue = oldComponent.getQuantity();

                        if (newValue == oldValue) {
                            newComponent.setComment("Без изменений");
                        }

                        if (newValue > oldValue) {
                            newComponent.setComment("Увеличено на " + (newValue - oldValue) + " ");
                        }

                        if (newValue < oldValue) {
                            newComponent.setComment("Уменьшено на " + (oldValue - newValue) + " ");
                        }
                    }
                }
            }


            //проверяет, если у компонента нет коментария(уменьшено или увелечено количество наименования),
            // то это новое наименование, и устанавливается позиция добавлена
            for (int i = 0; i < commonOrder.getComponents().size(); i++) {
                ItemCommonOrder newComponent = commonOrder.getComponents().get(i);

                if (newComponent.getComment() == null ) {
                    newComponent.setComment("Позиция добавлена");
                }
            }


            //перебираем старый заказ, если новая позиция не была добавлена, то добавляем ее с пометкой такая позиция была и сейчас удалена
            for (int i = 0; i < commonOrder1.getComponents().size(); i++) {
                ItemCommonOrder oldComponent = commonOrder1.getComponents().get(i);

                boolean check = false;

                for (int k = 0; k < commonOrder.getComponents().size(); k++) {
                    ItemCommonOrder newComponent = commonOrder.getComponents().get(k);

                    if (oldComponent.getTitle().equals(newComponent.getTitle())) {

                        check = true;
                    }
                }

                if (check == false) {
                    //установить коментарий компонент удален и добавить его в новый заказ
                    oldComponent.setComment("Позиция удалена");
                    commonOrder.getComponents().add(oldComponent);
                }

            }
            //commonOrderDao.deleteCommonOrder(commonOrder1);
        }
    }


    @Override
    @Transactional
    public void saveCommonOrder(CommonOrder commonOrder) {


        //большая проверка на старый заказ
        checkOrder (commonOrder);


        commonOrderDao.saveCommonOrder(commonOrder);

        System.out.println(commonOrder.getComponents().size());

        List<CommonOrder> commonOrders = new ArrayList<CommonOrder>();


        for (int i = 0; i < commonOrder.getComponents().size(); i++) {
            commonOrder.getComponents().get(i).setCommonOrder(commonOrder);
            itemCommonOrderDao.saveItemCommonOrder(commonOrder.getComponents().get(i));
        }


        for (int i = 0; i < commonOrder.getComponents().size(); i++) {

            if (!commonOrder.getComponents().get(i).getTitle().contains("тара") &
                    !commonOrder.getComponents().get(i).getTitle().contains("осква") &
                    commonOrder.getComponents().get(i).getMeasure().equals("кг")
                    ) {


                Item item = itemDao.getItemByName(commonOrder.getComponents().get(i).getTitle());

                if (item == null) {
                    ItemCommonOrder itemCommonOrder = new ItemCommonOrder();
                    itemCommonOrder.setCode(commonOrder.getComponents().get(i).getCode());
                    itemCommonOrder.setQuantity(commonOrder.getComponents().get(i).getQuantity());
                    itemCommonOrder.setCategory("laboratory");
                    itemCommonOrder.setComment(commonOrder.getComponents().get(i).getComment() + " Рецепт не найден");
                    itemCommonOrder.setCommonOrder(commonOrder);
                    itemCommonOrder.setTitle(commonOrder.getComponents().get(i).getTitle());
                    itemCommonOrderDao.saveItemCommonOrder(itemCommonOrder);
                    continue;
                }


                if (item.getComponents().size() > 0) {

                    ItemCommonOrder itemCommonOrder = new ItemCommonOrder();
                    itemCommonOrder.setCode(commonOrder.getComponents().get(i).getCode());
                    itemCommonOrder.setItem(item);
                    itemCommonOrder.setQuantity(commonOrder.getComponents().get(i).getQuantity());
                    itemCommonOrder.setCategory("laboratory");
                    itemCommonOrder.setComment(commonOrder.getComponents().get(i).getComment() + "На колеровку");
                    itemCommonOrder.setCommonOrder(commonOrder);
                    itemCommonOrder.setTitle(commonOrder.getComponents().get(i).getTitle());
                    itemCommonOrderDao.saveItemCommonOrder(itemCommonOrder);

                    double quantity = commonOrder.getComponents().get(i).getQuantity() * 1000 / item.getQuantity();

                    for (int k = 0; k < item.getComponents().size(); k++) {

                        Component component = item.getComponents().get(k);
                        Item itemComponent = component.getItem();
                        ItemCommonOrder itemCommonOrder2 = new ItemCommonOrder();
                        itemCommonOrder2.setItem(itemComponent);
                        itemCommonOrder2.setQuantity(itemComponent.getQuantity() * quantity);
                        itemCommonOrder2.setCategory("component");
                        itemCommonOrder2.setMeasure("г");
                        itemCommonOrder2.setComment("ингридиент");
                        itemCommonOrder2.setTitle(commonOrder.getComponents().get(i).getTitle());
                        itemCommonOrder2.setItemCommonOrder(itemCommonOrder);
                        itemCommonOrderDao.saveItemCommonOrder(itemCommonOrder2);
                    }
                }

            }


            if ((commonOrder.getComponents().get(i).getMeasure().contains("кг") | commonOrder.getComponents().get(i).getMeasure().contains("л"))
                    & commonOrder.getComponents().get(i).getTitle().contains("тара")) {



                //выделение из названия наименования объема тары
                double quantity = 0.0;
                String title = commonOrder.getComponents().get(i).getTitle();
                int start = title.indexOf("тара");
                int end = title.indexOf(")", start);
                String res = title.substring(start, end);
                System.out.println(res);

                String newString = new String();

                for (int j = 0; j < res.length(); j++) {

                    char c = res.charAt(j);
                    if ((c >= '0' & c <= '9') | c == ',') {

                        if (c == ',') {
                            newString = newString + ".";
                        } else {
                            newString = newString + c;
                        }
                    }
                }
                quantity = new Double(newString);



                double balance = commonOrder.getComponents().get(i).getQuantity() % quantity;

                Item item = itemDao.getItemByName(commonOrder.getComponents().get(i).getTitle());

                if (balance == 0) {
                    ItemCommonOrder itemCommonOrder = new ItemCommonOrder();
                    itemCommonOrder.setCode(commonOrder.getComponents().get(i).getCode());
                    itemCommonOrder.setQuantity(commonOrder.getComponents().get(i).getQuantity());
                    itemCommonOrder.setCategory("warehouse");
                    itemCommonOrder.setTitle(commonOrder.getComponents().get(i).getTitle());
                    itemCommonOrder.setComment(commonOrder.getComponents().get(i).getComment() + "Отгрузить");
                    itemCommonOrder.setCommonOrder(commonOrder);

                    if (item != null) {
                        itemCommonOrder.setItem(item);
                    } else {
                        itemCommonOrder.setComment(commonOrder.getComponents().get(i).getComment() + " компонент не найден");
                    }

                    itemCommonOrderDao.saveItemCommonOrder(itemCommonOrder);

                } else {

                    ItemCommonOrder itemCommonOrder = new ItemCommonOrder();
                    itemCommonOrder.setCode(commonOrder.getComponents().get(i).getCode());
                    itemCommonOrder.setQuantity(commonOrder.getComponents().get(i).getQuantity());
                    itemCommonOrder.setTitle(commonOrder.getComponents().get(i).getTitle());
                    itemCommonOrder.setCategory("laboratory");
                    itemCommonOrder.setComment(commonOrder.getComponents().get(i).getComment() + " Отмерять " + (balance * 1000) + " грамм");
                    itemCommonOrder.setCommonOrder(commonOrder);

                    if (item != null) {
                        itemCommonOrder.setItem(item);
                    } else {
                        itemCommonOrder.setComment(commonOrder.getComponents().get(i).getComment() + " компонент не найден");
                    }
                    itemCommonOrderDao.saveItemCommonOrder(itemCommonOrder);


                    if (commonOrder.getComponents().get(i).getQuantity() - quantity > 0) {

                        itemCommonOrder = new ItemCommonOrder();
                        itemCommonOrder.setCode(commonOrder.getComponents().get(i).getCode());
                        itemCommonOrder.setQuantity(commonOrder.getComponents().get(i).getQuantity());
                        itemCommonOrder.setCategory("warehouse");
                        itemCommonOrder.setTitle(commonOrder.getComponents().get(i).getTitle());
                        itemCommonOrder.setComment(commonOrder.getComponents().get(i).getComment() + " отгрузить " + (commonOrder.getComponents().get(i).getQuantity() - balance) * 1000);
                        itemCommonOrder.setCommonOrder(commonOrder);

                        if (item != null) {
                            itemCommonOrder.setItem(item);
                        } else {
                            itemCommonOrder.setComment(commonOrder.getComponents().get(i).getComment() + " компонент не найден");
                        }
                        itemCommonOrderDao.saveItemCommonOrder(itemCommonOrder);

                    }


                }
                continue;
            }


            Item item = itemDao.getItemByName(commonOrder.getComponents().get(i).getTitle());

            ItemCommonOrder itemCommonOrder = new ItemCommonOrder();
            itemCommonOrder.setCode(commonOrder.getComponents().get(i).getCode());
            itemCommonOrder.setQuantity(commonOrder.getComponents().get(i).getQuantity());
            itemCommonOrder.setCategory("warehouse");
            itemCommonOrder.setComment(commonOrder.getComponents().get(i).getComment() + "");
            itemCommonOrder.setTitle(commonOrder.getComponents().get(i).getTitle());
            itemCommonOrder.setCommonOrder(commonOrder);

            if (item != null) {
                itemCommonOrder.setItem(item);
            } else {
                itemCommonOrder.setComment(commonOrder.getComponents().get(i).getComment() + " компонент не найден");
            }

            itemCommonOrderDao.saveItemCommonOrder(itemCommonOrder);
            continue;


        }


    }

    @Override
    @Transactional
    public void updateCommonOrder(CommonOrder commonOrder) {
        commonOrderDao.updateCommonOrder(commonOrder);
    }

    @Override
    @Transactional
    public void deleteCommonOrder(CommonOrder commonOrder) {
        commonOrderDao.deleteCommonOrder(commonOrder);
    }

    @Override
    public ItemCommonOrder getItemCommonOrderById(int idItemCommonOrder) {
        return itemCommonOrderDao.getItemCommonOrderById(idItemCommonOrder);
    }

    @Override
    @Transactional
    public List<ItemCommonOrder> getItemsCommonOrdersByCommonOrderId(int idCommonOrder) {

        List<ItemCommonOrder> itemCommonOrders = commonOrderDao.getCommonOrderById(idCommonOrder).getComponents();
        System.out.println("itemCommonOrders.size = " + itemCommonOrders.size());
        return itemCommonOrders;
    }

    @Override
    @Transactional
    public void updateItemCommonOrder(ItemCommonOrder itemCommonOrder, int idCommonOrder) {
        //CommonOrder commonOrder = commonOrderDao.getCommonOrderById(idCommonOrder);
        //itemCommonOrder.setCommonOrder(commonOrder);

        ItemCommonOrder item = itemCommonOrderDao.getItemCommonOrderById(itemCommonOrder.getId());

        item.setDeferred(itemCommonOrder.isDeferred());
        item.setReady(itemCommonOrder.isReady());
        item.setReason(itemCommonOrder.getReason());

        itemCommonOrderDao.updateItemCommonOrder(item);


        CommonOrder commonOrder = item.getCommonOrder();
        boolean checkOrder = false;
        for(int i = 0; i < commonOrder.getComponents().size(); i++){
            if(commonOrder.getComponents().get(i).isDeferred()){
                checkOrder = true;
            }
        }
        commonOrder.setHasDeferred(checkOrder);

        commonOrderDao.updateCommonOrder(commonOrder);

        System.out.println("itemCommonOrder=" + itemCommonOrder + "item= " + item);

        /*itemCommonOrder.setCommonOrder(item.getCommonOrder());

        itemCommonOrderDao.updateItemCommonOrder(itemCommonOrder);*/

       /* for(int i = 0; i < commonOrder.getItems().size(); i++ ){
            //TODO сделать пересчет параметров
        }*/


    }

    @Override
    @Transactional
    public void returnFull(CommonOrder commonOrder) {
        CommonOrder commonOrder1 = commonOrderDao.getCommonOrderById(commonOrder.getId());


        for(int i = 0; i <commonOrder1.getComponents().size(); i++ ){
            commonOrder1.getComponents().get(i).setDeferred(false);
        }

        commonOrder1.setHasDeferred(false);
        commonOrder1.setDeferred(false);
    }

    @Override
    @Transactional
    public void updateComment(CommonOrder commonOrder) {
        CommonOrder commonOrder1 = commonOrderDao.getCommonOrderById(commonOrder.getId());
        commonOrder1.setComment(commonOrder.getComment());


    }
}
