package com.bonvio.dao.order;

import com.bonvio.model.order.ItemCommonOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Ivan on 24.02.2015.
 */
@Repository
public class ItemCommonOrderDaoImpl implements  ItemCommonOrderDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ItemCommonOrder getItemCommonOrderById(int idItemCommonOrder) {
        return entityManager.find(ItemCommonOrder.class, idItemCommonOrder);
    }

    @Override
    public void saveItemCommonOrder(ItemCommonOrder itemCommonOrder) {





        entityManager.persist(itemCommonOrder);
        System.out.println(itemCommonOrder);

/*
        for (int i = 0; i < itemCommonOrder.getComponents().size(); i++){
            itemCommonOrder.getComponents().get(i).setItemCommonOrder(itemCommonOrder);
        }*/



        for(int i = 0; i < itemCommonOrder.getComponents().size(); i++){

            itemCommonOrder.getComponents().get(i).setItemCommonOrder(itemCommonOrder);

            entityManager.persist(itemCommonOrder.getComponents().get(i));

        }

    }

    @Override
    public void updateItemCommonOrder(ItemCommonOrder itemCommonOrder) {
        entityManager.merge(itemCommonOrder);
    }

    @Override
    public void deleteItemCommonOrder(ItemCommonOrder itemCommonOrder) {
        entityManager.remove(itemCommonOrder);
    }


}
