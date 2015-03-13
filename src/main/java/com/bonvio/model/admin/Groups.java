package com.bonvio.model.admin;

import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ivan on 16.02.2015.
 */
@Entity
@Table (name = "groups")
public class Groups {

    public Groups() {
    }

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    private String title;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "groups")
    private Set<Role> roles = new HashSet<Role>(0);


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

    @JsonManagedReference

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
