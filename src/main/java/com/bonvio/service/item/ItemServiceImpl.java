package com.bonvio.service.item;

import com.bonvio.dao.item.ComponentDao;
import com.bonvio.dao.item.ItemDao;
import com.bonvio.model.item.Component;
import com.bonvio.model.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Ivan on 13.03.2015.
 */

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDao itemDao;

    @Autowired
    ComponentDao componentDao;


    @Override
    public List<Item> getAllItems() {
        List<Item> items = itemDao.getAllItems();
        return items;
    }

    @Override
    public List<Item> getItemsByExpression(String expression) {
        return itemDao.getItemByExpression(expression);
    }

    @Override
    @Transactional
    public void updateItem(Item item) {
        itemDao.updateItem(item);
    }

    @Override
    public void removeItem(int id) {
        System.out.println("метод removeItem не работает");
        //TODO
    }

    @Override
    @Transactional
    public void createItem(Item item) {
        itemDao.saveItem(item);
        for(int i = 0; i< item.getComponents().size(); i++){
            item.getComponents().get(i).setParentItem(item);
            componentDao.saveComponent(item.getComponents().get(i));
        }
    }

    @Override
    @Transactional
    public void addComponent(Component component, int idItem) {
        Item item = itemDao.getItemById(idItem);
        component.setItem(item);
        componentDao.saveComponent(component);
    }

    @Override
    @Transactional
    public void updateComponent(Component component) {
        Component foundComponent =  componentDao.getComponentById(component.getId());
        foundComponent.setMeasure(component.getMeasure());
        foundComponent.setQuantity(component.getQuantity());
    }

    @Override
    @Transactional
    public void removeComponent(int id) {
        Component component = new Component();
        component.setId(id);
        componentDao.deleteComponent(component);
    }

    @Override
    public List<Component> getItemByExpression(String expression) {
        //TODO  можно не искать компонент
        System.out.println(" метод getItemByExpression не работает");
        return null;
    }
}
