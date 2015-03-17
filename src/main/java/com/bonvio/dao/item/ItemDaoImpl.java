package com.bonvio.dao.item;


import com.bonvio.model.item.Item;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 08.10.2014.
 */

@Repository
@Transactional
public class ItemDaoImpl implements ItemDao {

    @PersistenceContext()
    EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<Item> getAllItems() {
        return entityManager.createNativeQuery("SELECT * FROM item where type = 'component'", Item.class).getResultList();
    }

    @Override
    public List<Item> getItemByExpression(String query) {

        query = query.toLowerCase();

        char[] queryArray = query.toCharArray();

        query = "SELECT * FROM item WHERE type = 'component' and LOWER(name) LIKE '%";
        for (int i = 0; i < queryArray.length; i++) {
            query += queryArray[i] + "%";
        }
        query += "' LIMIT 100";//LIMIT 30

        @SuppressWarnings("unchecked")
        List<Item> items = entityManager.createNativeQuery(query, Item.class).getResultList();
        return items;
    }


    @Override
    public void saveItem(Item item) {
        entityManager.persist(item);
    }


    @Override
    public Item getItemById(long idItem) {
        return (Item)entityManager.createNativeQuery("SELECT * FROM item where id =:idItem", Item.class).setParameter("idItem", idItem).getSingleResult();
    }

    @Override
    public List<Item> getAllRecipes() {
        return entityManager.createNativeQuery("SELECT * FROM item where type = 'recipe'", Item.class).getResultList();
    }

    @Override
    public List<Item> getRecipesByExpression(String query) {

        query = query.toLowerCase();

        char[] queryArray = query.toCharArray();

        query = "SELECT * FROM item WHERE type = 'recipe' and LOWER(name) LIKE '%";
        for (int i = 0; i < queryArray.length; i++) {
            query += queryArray[i] + "%";
        }
        query += "' LIMIT 100";//LIMIT 30

        @SuppressWarnings("unchecked")
        List<Item> items = entityManager.createNativeQuery(query, Item.class).getResultList();
        return items;
    }

    @Override
    public void updateItem(Item item) {
        entityManager.merge(item);
    }

    @Override
    public void removeRecipe(Item recipe) {
        entityManager.remove(recipe);
    }

    @Override
    public List<String> getCategoriesStrings() {
        return entityManager.createNativeQuery("SELECT category FROM item GROUP BY category").getResultList();
    }

    @Override
    public Item getItemByName(String itemName) {
        List<Item> items = new ArrayList<Item>();
        items.addAll(entityManager.createNativeQuery("select * from item where name =:itemName and type = 'component'", Item.class).setParameter("itemName", itemName).getResultList());

        if(items.size()>0){
            return items.get(0);
        }

        return null;
    }

    @Override
    public Item getRecipeByName(String recipeName) {
        List<Item> items = new ArrayList<Item>();
        items.addAll(entityManager.createNativeQuery("select * from item where name =:recipeName and type = 'recipe'", Item.class).setParameter("recipeName", recipeName).getResultList());

        if(items.size()>0){
            return items.get(0);
        }

        return null;
    }


}
