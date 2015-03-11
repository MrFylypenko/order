package com.bonvio.model.formula;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Created by Ivan on 10.03.2015.
 */

@Entity
@Table (name = "component")
public class Component {

    public Component(){}

    public Component(int id){
        this.id = id;
    }

    @Id
    private int id;
    private String title;
    private double quantity;

    @ManyToOne
    private Recipe recipe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
