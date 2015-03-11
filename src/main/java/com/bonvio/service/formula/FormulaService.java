package com.bonvio.service.formula;

import com.bonvio.model.formula.Component;
import com.bonvio.model.formula.Recipe;

import java.util.List;

/**
 * Created by Ivan on 10.03.2015.
 */
public interface FormulaService {

    public List<Recipe> getAllRecipes();

    public List<Recipe> getRecipesByExpression(String expression);

    public void updateRecipe(Recipe recipe);

    public void removeRecipe(int id);

    public void createRecipe(Recipe recipe);

    public void addComponent(Component component, int idRecipe);

    public void updateComponent(Component component);

    public void removeComponent(int id);

    public List<Component> getItemByExpression (String expression);


}
