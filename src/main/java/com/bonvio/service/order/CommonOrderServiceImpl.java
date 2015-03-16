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
        System.out.println("отдал заказы");
        return commonOrderDao.getAllCommonOrders();
    }

    @Override
    @Transactional
    public void saveCommonOrder(CommonOrder commonOrder) {

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
                    itemCommonOrder.setComment("Рецепт не найден");
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
                    itemCommonOrder.setComment("На колеровку");
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
                    itemCommonOrder.setComment("Отгрузить");
                    itemCommonOrder.setCommonOrder(commonOrder);

                    if (item != null) {
                        itemCommonOrder.setItem(item);
                    } else {
                        itemCommonOrder.setComment(itemCommonOrder.getComment() + " компонент не найден");
                    }

                    itemCommonOrderDao.saveItemCommonOrder(itemCommonOrder);

                } else {

                    ItemCommonOrder itemCommonOrder = new ItemCommonOrder();
                    itemCommonOrder.setCode(commonOrder.getComponents().get(i).getCode());
                    itemCommonOrder.setQuantity(commonOrder.getComponents().get(i).getQuantity());
                    itemCommonOrder.setTitle(commonOrder.getComponents().get(i).getTitle());
                    itemCommonOrder.setCategory("laboratory");
                    itemCommonOrder.setComment("отмерять " + (balance*1000) + " грамм");
                    itemCommonOrder.setCommonOrder(commonOrder);

                    if (item != null) {
                        itemCommonOrder.setItem(item);
                    } else {
                        itemCommonOrder.setComment(itemCommonOrder.getComment() + " компонент не найден");
                    }
                    itemCommonOrderDao.saveItemCommonOrder(itemCommonOrder);


                    if (commonOrder.getComponents().get(i).getQuantity() - quantity > 0) {

                        itemCommonOrder = new ItemCommonOrder();
                        itemCommonOrder.setCode(commonOrder.getComponents().get(i).getCode());
                        itemCommonOrder.setQuantity(commonOrder.getComponents().get(i).getQuantity());
                        itemCommonOrder.setCategory("warehouse");
                        itemCommonOrder.setTitle(commonOrder.getComponents().get(i).getTitle());
                        itemCommonOrder.setComment("отгрузить " + (commonOrder.getComponents().get(i).getQuantity() - balance)*1000);
                        itemCommonOrder.setCommonOrder(commonOrder);

                        if (item != null) {
                            itemCommonOrder.setItem(item);
                        } else {
                            itemCommonOrder.setComment(itemCommonOrder.getComment() + " компонент не найден");
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
            itemCommonOrder.setComment("");
            itemCommonOrder.setTitle(commonOrder.getComponents().get(i).getTitle());
            itemCommonOrder.setCommonOrder(commonOrder);

            if (item != null) {
                itemCommonOrder.setItem(item);
            } else {
                itemCommonOrder.setComment(itemCommonOrder.getComment() + " компонент не найден");
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
        itemCommonOrder.setCommonOrder(item.getCommonOrder());

        itemCommonOrderDao.updateItemCommonOrder(itemCommonOrder);

       /* for(int i = 0; i < commonOrder.getItems().size(); i++ ){
            //TODO сделать пересчет параметров
        }*/


    }
}
