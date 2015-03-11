package com.bonvio.dao.formula;

import com.bonvio.model.formula.Recipe;

import java.util.List;

/**
 * Created by Ivan on 10.03.2015.
 */
public interface RecipeDao {

    public Recipe getRecipeById(int id);
    public List<Recipe> getAllRecipes ();
    public List<Recipe> getRecipesByExpression (String expression);
    public void saveRecipe (Recipe recipe);
    public void updateRecipe (Recipe recipe);
    public void deleteRecipe (Recipe recipe);

}
