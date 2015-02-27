package com.bonvio.model.admin;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name = "users")
@NamedQuery(name = "selectAllUsers", query = "SELECT a FROM User a")
public class User {

    public User() {
    }

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        //this.enabled = enabled;
    }

    public User(String username, String password, boolean enabled,
                Set<UserRole> userRoles) {
        this.username = username;
        this.password = password;
        //this.enabled = enabled;
        this.userRoles = userRoles;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String path;
    private String role;

    /*
    * обязательно необходимо проверить MyUserDetailsService метод private List<GrantedAuthority> buildUserAuthority
    * в нем выбираются UserRole с установленным флагом checked:true для сессии, и выберуться не все роли
    * */
    private Set<UserRole> userRoles = new HashSet<UserRole>(0);

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", path='" + path + '\'' +
                ", role='" + role + '\'' +
                ", userRoles=" + userRoles.size() +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "username", unique = true, nullable = false, length = 45)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false, length = 60)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
