package com.bonvio.dao.item;


import com.bonvio.model.item.Item;

import java.util.List;

/**
 * Created by Ivan on 08.10.2014.
 */
public interface ItemDao {

	public List<Item> getAllItems();

	public Item getItemById(long id);

	public List<Item> getItemByExpression(String query);

	public void saveItem(Item item);

    public void updateItem(Item item);

    public List<String> getCategoriesStrings();


}
