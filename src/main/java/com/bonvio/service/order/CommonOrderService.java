package com.bonvio.service.order;

import com.bonvio.model.order.CommonOrder;
import com.bonvio.model.order.ItemCommonOrder;

import java.util.List;

/**
 * Created by Ivan on 24.02.2015.
 */
public interface CommonOrderService {

    public CommonOrder getCommonOrderById (int idCommonOrder);
    public List<CommonOrder> getAllCommonOrders ();
    public void saveCommonOrder (CommonOrder commonOrder);
    public void updateCommonOrder (CommonOrder commonOrder);
    public void deleteCommonOrder (CommonOrder commonOrder);
    public ItemCommonOrder getItemCommonOrderById (int idItemCommonOrder);
    public List<ItemCommonOrder> getItemsCommonOrdersByCommonOrderId (int idItemCommonOrder);
    public void updateItemCommonOrder (ItemCommonOrder itemCommonOrder , int idCommonOrder);
    public void returnFull (CommonOrder commonOrder);


}
