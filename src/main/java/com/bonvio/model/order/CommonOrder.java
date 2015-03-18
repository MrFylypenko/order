package com.bonvio.model.order;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 24.02.2015.
 */
@Entity
@Table (name = "commonorder")
public class CommonOrder {

    public CommonOrder(){}

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;
    private String customer;
    private String date;
    private int number;
    private int status;
    private int priority;
    private boolean deferred;
    private boolean hasDeferred;

    @OneToMany(mappedBy = "commonOrder")
    private List <ItemCommonOrder> components = new ArrayList<ItemCommonOrder>();

    @Override
    public String toString() {
        return "CommonOrder{" +
                "id=" + id +
                ", customer='" + customer + '\'' +
                ", date='" + date + '\'' +
                ", number=" + number +
                ", status=" + status +
                ", priority=" + priority +
                ", deferred=" + deferred +
                ", components=" + components +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDeferred() {
        return deferred;
    }

    public void setDeferred(boolean deferred) {
        this.deferred = deferred;
    }

    public boolean isHasDeferred() {
        return hasDeferred;
    }

    public void setHasDeferred(boolean hasDeferred) {
        this.hasDeferred = hasDeferred;
    }

    @JsonIgnore
    @JsonManagedReference
    public List<ItemCommonOrder> getComponents() {
        return components;
    }

    public void setComponents(List<ItemCommonOrder> components) {
        this.components = components;
    }
}
