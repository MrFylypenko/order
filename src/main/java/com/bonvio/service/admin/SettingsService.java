package com.bonvio.service.admin;

import com.bonvio.model.admin.Settings;
import org.springframework.stereotype.Service;

/**
 * Created by Vano on 02.03.2015.
 */

public interface SettingsService {

    public Settings getSettings ();

    public void updateSettings (Settings settings);
}
