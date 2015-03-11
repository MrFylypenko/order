package com.bonvio.dao.formula;

import com.bonvio.model.formula.Recipe;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Ivan on 10.03.2015.
 */

@Repository
public class RecipeDaoImpl implements RecipeDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Recipe getRecipeById(int id) {
        return entityManager.find(Recipe.class, id);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return entityManager.createNativeQuery("select * from recipe", Recipe.class).getResultList();
    }

    @Override
    public List<Recipe> getRecipesByExpression(String expression) {

        return null; //entityManager.createNativeQuery("select * from recipe ", Recipe.class).getResultList();
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        entityManager.persist(recipe);
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        entityManager.merge(recipe);
    }

    @Override
    public void deleteRecipe(Recipe recipe) {
        entityManager.remove(recipe);
    }
}
