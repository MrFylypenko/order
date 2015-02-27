package com.bonvio.model.order;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Ivan on 24.02.2015.
 */
@Entity
@Table (name = "itemorder")
public class ItemCommonOrder {

    public ItemCommonOrder(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String code;
    private String title;
    private String quantity;
    private String category;
    private String comment;
    private boolean ready;
    private boolean deferred;

    @ManyToOne
    private CommonOrder commonOrder;

    @Override
    public String toString() {
        return "ItemCommonOrder{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", quantity='" + quantity + '\'' +
                ", category='" + category + '\'' +
                ", comment='" + comment + '\'' +
                ", ready=" + ready +
                ", deferred=" + deferred +
                /*", commonOrder=" + commonOrder +*/
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isDeferred() {
        return deferred;
    }

    public void setDeferred(boolean deferred) {
        this.deferred = deferred;
    }

    @JsonIgnore
    @JsonBackReference
    public CommonOrder getCommonOrder() {
        return commonOrder;
    }

    public void setCommonOrder(CommonOrder commonOrder) {
        this.commonOrder = commonOrder;
    }
}
