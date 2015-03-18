package com.bonvio.dao.order;

import com.bonvio.model.order.CommonOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Ivan on 24.02.2015.
 */
@Repository
public class CommonOrderDaoImpl implements CommonOrderDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CommonOrder getCommonOrderById(int idCommonOrder) {
        return entityManager.find(CommonOrder.class, idCommonOrder);
    }

    @Override
    public List<CommonOrder> getAllCommonOrders() {
        return entityManager.createNativeQuery("select * from commonorder", CommonOrder.class).getResultList();
    }

    @Override
    public void saveCommonOrder(CommonOrder commonOrder) {
        entityManager.persist(commonOrder);

    }

    @Override
    public void updateCommonOrder(CommonOrder commonOrder) {
        entityManager.merge(commonOrder);
    }

    @Override
    public void deleteCommonOrder(CommonOrder commonOrder) {
        entityManager.remove(commonOrder);
    }
}
