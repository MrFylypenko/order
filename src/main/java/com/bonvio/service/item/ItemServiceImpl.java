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
    public List<Item> getAllRecipes() {
        List<Item> recipes = itemDao.getAllRecipes();
        return recipes;
    }

    @Override
    public List<Item> getRecipesByExpression(String expression) {
        return itemDao.getRecipesByExpression(expression);
    }

    @Override
    @Transactional
    public void updateItem(Item item) {
        itemDao.updateItem(item);
    }

    @Override
    @Transactional
    public void updateRecipe(Item recipe) {
        Item found = itemDao.getItemById(recipe.getId());
        for(int i = 0; i < found.getComponents().size(); i++){
            Component component = new Component();
            component.setId(found.getComponents().get(i).getId());
            componentDao.deleteComponent(component);
        }

        for(int i = 0; i< recipe.getComponents().size(); i++){

            recipe.getComponents().get(i).setParentItem(found);

            Component component = new Component();
            component.setQuantity(recipe.getComponents().get(i).getQuantity());
            component.setMeasure(recipe.getComponents().get(i).getMeasure());
            component.setParentItem(found);
            component.setItem(recipe.getComponents().get(i).getItem());

            componentDao.saveComponent(component);
        }
    }

    @Override
    @Transactional
    public void removeItem (int id) {
        Item item = itemDao.getItemById(id);
        if (item.getType().equals("component")){
            itemDao.removeRecipe(item);
        }
    }

    @Override
    @Transactional
    public void removeRecipe(int id) {
        Item item = itemDao.getItemById(id);
        if (item.getType().equals("recipe")){
            itemDao.removeRecipe(item);
        }
    }

    @Override
    @Transactional
    public void createItem(Item item) {
        item.setType("component");
        itemDao.saveItem(item);
    }

    @Override
    @Transactional
    public void createRecipe(Item recipe) {
        recipe.setType("recipe");
        itemDao.saveItem(recipe);
        for(int i = 0; i< recipe.getComponents().size(); i++){
            recipe.getComponents().get(i).setParentItem(recipe);
            componentDao.saveComponent(recipe.getComponents().get(i));
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
}
