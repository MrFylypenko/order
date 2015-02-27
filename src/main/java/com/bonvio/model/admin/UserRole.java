package com.bonvio.model.admin;

import org.codehaus.jackson.annotate.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user_roles", uniqueConstraints = @UniqueConstraint(columnNames = { "role", "username" }))
public class UserRole{

    public UserRole() {
    }

    public UserRole(User user, String role) {
        this.user = user;
        this.role = role;
    }

	private Integer id;
    private String title;
	private String role;
    private boolean checked;

    private User user;

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", role='" + role + '\'' +
                ", checked=" + checked +
                ", user.getUsername=" + user.getUsername() +
                '}';
    }

    @Id
    @GeneratedValue
	@Column(name = "user_role_id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer userRoleId) {
		this.id = userRoleId;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "role", nullable = false, length = 45)
	public String getRole() {
		return this.role;
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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}