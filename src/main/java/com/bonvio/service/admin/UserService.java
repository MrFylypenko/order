package com.bonvio.service.admin;

import java.util.List;

import com.bonvio.model.admin.Settings;
import com.bonvio.model.admin.User;

public interface UserService {
	
	public List<User> getAllUsers();

	public void addUser(User user);
	
	public User findByUserName(String username);
	
	public String encode(String pass);

    public void updateUser(User user);

    public void updateUserRoles(User user);

    public void createUser (User user);



   /* public void testDBF ();*/
}
