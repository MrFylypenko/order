package com.bonvio.service.density;

import com.bonvio.dao.density.DensityDao;
import com.bonvio.model.density.Density;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Ivan on 27.02.2015.
 */
@Service
public class DensityServiceImpl implements DensityService {

    @Autowired
    DensityDao densityDao;

    @Override
    public Density getDensityById(int idDensity) {
        return densityDao.getDensityById(idDensity);
    }

    @Override
    public List<Density> getAllDensities() {
        return densityDao.getAllDensities();
    }

    @Override
    @Transactional
    public void addDensity(Density density) {
        densityDao.addDensity(density);
    }

    @Override
    @Transactional
    public void updateDensity(Density density) {
        densityDao.updateDensity(density);
    }

    @Override
    @Transactional
    public void deleteDensity(Density density) {
        densityDao.deleteDensity(density);
    }
}
