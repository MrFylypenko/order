package com.bonvio.model.density;

import javax.persistence.*;

/**
 * Created by Ivan on 27.02.2015.
 */
@Entity
@Table(name = "density")
public class Density {

    public Density (){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String component;
    private String source;
    private double factor;
    private String result;

    @Override
    public String toString() {
        return "Density{" +
                "id=" + id +
                ", component='" + component + '\'' +
                ", source='" + source + '\'' +
                ", factor=" + factor +
                ", result='" + result + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
