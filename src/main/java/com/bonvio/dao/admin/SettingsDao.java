package com.bonvio.dao.admin;

import com.bonvio.model.admin.Settings;

/**
 * Created by Vano on 02.03.2015.
 */
public interface SettingsDao {

    public Settings getSettings ();
    public void updateSettings (Settings settings);

}
