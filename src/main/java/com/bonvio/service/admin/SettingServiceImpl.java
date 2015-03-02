package com.bonvio.service.admin;

import com.bonvio.dao.admin.SettingsDao;
import com.bonvio.model.admin.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Vano on 02.03.2015.
 */
@Service
public class SettingServiceImpl implements SettingsService {

    @Autowired
    SettingsDao settingsDao;


    @Override
    public Settings getSettings() {
        return settingsDao.getSettings();
    }

    @Override
    @Transactional
    public void updateSettings(Settings settings) {
        settingsDao.updateSettings(settings);
    }
}
