package com.bonvio.model.item;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private Item parentItem;

    /// Test
    @ManyToOne
    private Item item;


    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", measure='" + measure + '\'' +
                //", item=" + item +
                '}';
    }

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

    @JsonBackReference
    public Item getParentItem() {
        return parentItem;
    }

    public void setParentItem(Item parentItem) {
        this.parentItem = parentItem;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
