package com.bonvio.model.admin;

import javax.persistence.*;

/**
 * Created by Vano on 02.03.2015.
 */
@Entity
@Table(name = "settings")
public class Settings {

    public Settings() {
    }

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;
    private boolean printDefault;
    private boolean uploadDefault;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPrintDefault() {
        return printDefault;
    }

    public void setPrintDefault(boolean printDefault) {
        this.printDefault = printDefault;
    }

    public boolean isUploadDefault() {
        return uploadDefault;
    }

    public void setUploadDefault(boolean uploadDefault) {
        this.uploadDefault = uploadDefault;
    }
}
