package com.bonvio.model.formula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ivan on 10.03.2015.
 */

@Entity
@Table (name = "recipe")
public class Recipe {

    public Recipe(){

    }

    public Recipe(int id){
        this.id = id;
    }

    @Id
    private int id;
    private String title;

    @OneToMany (mappedBy = "recipe", fetch = FetchType.LAZY)
    private Set<Component> component = new HashSet<Component>();


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

    public Set<Component> getComponent() {
        return component;
    }

    public void setComponent(Set<Component> component) {
        this.component = component;
    }
}
