package com.bonvio.dao.density;

import com.bonvio.model.density.Density;

import java.util.List;

/**
 * Created by Ivan on 27.02.2015.
 */
public interface DensityDao {

    public Density getDensityById (int idDensity);
    public List<Density> getAllDensities ();
    public void addDensity (Density density);
    public void updateDensity (Density density);
    public void deleteDensity (Density density);

}
