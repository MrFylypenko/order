package com.bonvio.dao.formula;

import com.bonvio.model.formula.Component;
import com.bonvio.model.formula.Recipe;

import java.util.List;

/**
 * Created by Ivan on 10.03.2015.
 */
public interface ComponentDao {

    public Component getComponentById (int id);
    public List<Component> getAllComponents ();
    public void saveComponent (Component component);
    public void updateComponent (Component component);
    public void deleteComponent (Component component);

}
