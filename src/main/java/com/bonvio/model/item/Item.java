package com.bonvio.model.item;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 12.03.2015.
 */

@Entity
@Table(name = "item")
public class Item {

    public Item() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String category;

    String name;

    double warehouse;

    double laboratory;

    String measure;

    String username;

    double density;

    String type;

    double quantity;

    @OneToMany (mappedBy = "parentItem", fetch = FetchType.EAGER)
    List <Component> components = new ArrayList<Component>();

    //Test
    @OneToMany (mappedBy = "item", fetch = FetchType.EAGER)
    List <Component> involving = new ArrayList<Component>();


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", warehouse=" + warehouse +
                ", laboratory=" + laboratory +
                ", measure='" + measure + '\'' +
                ", username='" + username + '\'' +
                ", density=" + density +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", components=" + components +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(double warehouse) {
        this.warehouse = warehouse;
    }

    public double getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(double laboratory) {
        this.laboratory = laboratory;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @JsonManagedReference
    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    @JsonIgnore
    public List<Component> getInvolving() {
        return involving;
    }

    public void setInvolving(List<Component> involving) {
        this.involving = involving;
    }
}
