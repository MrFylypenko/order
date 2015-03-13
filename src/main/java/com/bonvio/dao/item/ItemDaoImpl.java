package com.bonvio.dao.item;


import com.bonvio.model.item.Item;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        return entityManager.createNativeQuery("SELECT * FROM item", Item.class).getResultList();
    }

    @Override
    public List<Item> getItemByExpression(String query) {

        query = query.toLowerCase();

        char[] queryArray = query.toCharArray();

        query = "SELECT * FROM item WHERE LOWER(name) LIKE '%";
        for (int i = 0; i < queryArray.length; i++) {
            query += queryArray[i] + "%";
        }
        query += "' AND type <> 'recipe'";//LIMIT 30

        @SuppressWarnings("unchecked")
        List<Item> items = entityManager.createNativeQuery(query, Item.class).getResultList();
        return items;
    }


    @Override
    public void saveItem(Item item) {
        entityManager.persist(item);
    }


    @Override
    public Item getItemById(long id) {
        return entityManager.find(Item.class, id);
    }

    @Override
    public void updateItem(Item item) {
        entityManager.merge(item);
    }

    @Override
    public List<String> getCategoriesStrings() {
        return entityManager.createNativeQuery("SELECT category FROM item GROUP BY category").getResultList();
    }
}
