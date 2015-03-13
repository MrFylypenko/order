package com.bonvio.dao.item;

import com.bonvio.model.item.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Ivan on 13.03.2015.
 */
@Repository
public class ComponentDaoImpl implements ComponentDao{


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Component getComponentById(int id) {
        return entityManager.find(Component.class, id);
    }

    @Override
    public List<Component> getAllComponents() {
        return entityManager.createNativeQuery("select * from component", Component.class).getResultList();
    }

    @Override
    public void saveComponent(Component component) {
        entityManager.persist(component);
    }

    @Override
    public void updateComponent(Component component) {
        entityManager.merge(component);
    }

    @Override
    public void deleteComponent(Component component) {
        entityManager.remove(component);
    }
}
