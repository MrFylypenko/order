package com.bonvio.service.formula;

import com.bonvio.dao.formula.ComponentDao;
import com.bonvio.dao.formula.RecipeDao;
import com.bonvio.model.formula.Component;
import com.bonvio.model.formula.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Ivan on 10.03.2015.
 */
@Service
public class FormulaServiceImpl implements FormulaService{

    @Autowired
    ComponentDao componentDao;

    @Autowired
    RecipeDao recipeDao;


    @Override
    @Transactional
    public List<Recipe> getAllRecipes() {
        return recipeDao.getAllRecipes();
    }

    @Override
    @Transactional
    public List<Recipe> getRecipesByExpression(String expression) {
        return recipeDao.getRecipesByExpression(expression);
    }

    @Override
    @Transactional
    public void updateRecipe(Recipe recipe) {
        recipeDao.updateRecipe(recipe);
    }

    @Override
    @Transactional
    public void removeRecipe(int id) {
        recipeDao.deleteRecipe(new Recipe (id));
    }

    @Override
    @Transactional
    public void createRecipe(Recipe recipe) {
        recipeDao.saveRecipe(recipe);
    }

    @Override
    @Transactional
    public void addComponent(Component component, int idRecipe) {
        component.setRecipe(recipeDao.getRecipeById(idRecipe));
        componentDao.saveComponent(component);
    }

    @Override
    @Transactional
    public void updateComponent(Component component) {
        Component component1 = componentDao.getComponentById(component.getId());
        component.setRecipe(component1.getRecipe());
        componentDao.updateComponent(component);
    }

    @Override
    @Transactional
    public void removeComponent(int id) {
        componentDao.deleteComponent(new Component(id));
    }

    @Override
    @Transactional
    public  List<Component>  getItemByExpression(String expression) {
        System.out.println("метод getItemByExpression в сервисе не работает");
        return null;
    }
}
