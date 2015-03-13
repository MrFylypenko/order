package com.bonvio.dao.item;

import com.bonvio.model.item.Component;

import java.util.List;

/**
 * Created by Ivan on 13.03.2015.
 */
public interface ComponentDao {

    public Component getComponentById (int id);
    public List<Component> getAllComponents ();
    public void saveComponent (Component component);
    public void updateComponent (Component component);
    public void deleteComponent (Component component);

}
