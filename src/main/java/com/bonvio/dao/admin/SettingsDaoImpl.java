package com.bonvio.dao.admin;

import com.bonvio.model.admin.Settings;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Vano on 02.03.2015.
 */
@Repository
public class SettingsDaoImpl implements SettingsDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Settings getSettings() {
        return entityManager.find(Settings.class, 1);
    }

    @Override
    public void updateSettings(Settings settings) {
         entityManager.merge(settings);
    }

}
