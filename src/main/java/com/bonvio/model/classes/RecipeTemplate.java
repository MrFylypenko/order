package com.bonvio.model.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 16.03.2015.
 */
public class RecipeTemplate {

    private String name;
    private double quantity;
    private String percent;
    private List <RecipeComponentTemplate> components = new ArrayList<RecipeComponentTemplate>();


    @Override
    public String toString() {
        return "RecipeTemplate{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", components=" + components +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public List<RecipeComponentTemplate> getComponents() {
        return components;
    }

    public void setComponents(List<RecipeComponentTemplate> components) {
        this.components = components;
    }
}
