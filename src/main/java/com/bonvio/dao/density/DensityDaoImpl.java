package com.bonvio.dao.density;

import com.bonvio.model.density.Density;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Ivan on 27.02.2015.
 */
@Repository
public class DensityDaoImpl implements DensityDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Density getDensityById(int idDensity) {
        return entityManager.find(Density.class, idDensity);
    }

    @Override
    public List<Density> getAllDensities() {
        return entityManager.createNativeQuery("select * from density", Density.class).getResultList();
    }

    @Override
    public void addDensity(Density density) {
        entityManager.persist(density);
    }

    @Override
    public void updateDensity(Density density) {
        entityManager.merge(density);
    }

    @Override
    public void deleteDensity(Density density) {
        entityManager.remove(density);
    }
}
