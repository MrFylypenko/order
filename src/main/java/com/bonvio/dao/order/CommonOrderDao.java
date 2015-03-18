package com.bonvio.dao.order;

import com.bonvio.model.order.CommonOrder;

import java.util.List;

/**
 * Created by Ivan on 24.02.2015.
 */
public interface CommonOrderDao {

    public CommonOrder getCommonOrderById (int idCommonOrder);
    public CommonOrder getCommonOrderByOrderNumber (int commonOrderNumber);
    public List<CommonOrder> getAllCommonOrders ();
    public void saveCommonOrder (CommonOrder commonOrder);
    public void updateCommonOrder (CommonOrder commonOrder);
    public void deleteCommonOrder (CommonOrder commonOrder);

}
