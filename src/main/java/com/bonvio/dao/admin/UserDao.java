package com.bonvio.dao.admin;

import java.util.List;

import com.bonvio.model.admin.User;

public interface UserDao {

	public void addUser(User user);

	public List<User> getAllUsers();

	public User findByUserName(String username);

	public String encode(String pass);

    public void updateUser(User user);

    public void updateUserRoles(User user);

    public void createUser (User user);

}
