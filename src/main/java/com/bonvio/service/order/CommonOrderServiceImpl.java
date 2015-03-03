package com.bonvio.service.order;

import com.bonvio.dao.order.CommonOrderDao;
import com.bonvio.dao.order.ItemCommonOrderDao;
import com.bonvio.model.order.CommonOrder;
import com.bonvio.model.order.ItemCommonOrder;
import com.bonvio.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Ivan on 24.02.2015.
 */
@Service
public class CommonOrderServiceImpl implements CommonOrderService{

    @Autowired
    CommonOrderDao commonOrderDao;

    @Autowired
    ItemCommonOrderDao itemCommonOrderDao;


    @Autowired
    ExcelService excelService;


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
        for (int i = 0; i < commonOrder.getItems().size(); i++){
            commonOrder.getItems().get(i).setCommonOrder(commonOrder);
            itemCommonOrderDao.saveItemCommonOrder(commonOrder.getItems().get(i));
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

        List<ItemCommonOrder> itemCommonOrders = commonOrderDao.getCommonOrderById(idCommonOrder).getItems();
        System.out.println("itemCommonOrders.size = "+itemCommonOrders.size());
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
