package com.bonvio.model.item;

import javax.persistence.*;

/**
 * Created by Ivan on 12.03.2015.
 */

@Entity
@Table (name = "component")
public class Component {

    public Component (){

    }

    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO)
    private int id;
    private double quantity;
    private String measure;

    @ManyToOne
    private Item item;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
