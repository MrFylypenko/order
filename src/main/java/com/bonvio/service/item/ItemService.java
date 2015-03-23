package com.bonvio.service.item;

import com.bonvio.model.item.Component;
import com.bonvio.model.item.Item;

import java.util.List;

/**
 * Created by Ivan on 13.03.2015.
 */
public interface ItemService {

    public List<Item> getAllItems();

    public List<Item> getItemsByExpression(String expression);

    public List<Item> getAllRecipes();

    public List<Item> getRecipesByExpression(String expression);

    public void updateItem(Item item);

    public void updateRecipe(Item recipe);

    public void removeItem(int id);

    public void removeRecipe(int id);

    public void createItem(Item item);

    public void createRecipe(Item recipe);

    public void addComponent(Component component, int idItem);

    public void updateComponent(Component component);

    public void removeComponent(int id);

    public Item getItemByName(String itemName);

    public Item getRecipeByName (String recipeName);

}
