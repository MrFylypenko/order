package com.bonvio.dao.order;

import com.bonvio.model.order.CommonOrder;
import com.bonvio.model.order.ItemCommonOrder;

import java.util.List;

/**
 * Created by Ivan on 24.02.2015.
 */
public interface ItemCommonOrderDao {

    public ItemCommonOrder getItemCommonOrderById (int idItemCommonOrder);
    public void saveItemCommonOrder (ItemCommonOrder itemCommonOrder);
    public void updateItemCommonOrder (ItemCommonOrder itemCommonOrder);
    public void deleteItemCommonOrder (ItemCommonOrder itemCommonOrder);


}
