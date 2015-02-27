package com.bonvio.model.admin;

import org.codehaus.jackson.annotate.JsonBackReference;

import javax.persistence.*;

/**
 * Created by Ivan on 09.02.2015.
 */
@Entity
@Table(name = "role")
public class Role {

    public Role() {
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String role;
    private boolean checked;

    @ManyToOne(fetch = FetchType.LAZY)
    private Groups groups;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", role='" + role + '\'' +
                ", checked=" + checked +
                ", groups=" + groups.getTitle() +
                '}';
    }

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

    @JsonBackReference

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
