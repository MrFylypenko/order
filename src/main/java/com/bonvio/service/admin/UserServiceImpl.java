package com.bonvio.service.admin;

import java.util.List;


import com.bonvio.dao.admin.SettingsDao;
import com.bonvio.model.admin.Settings;
import com.bonvio.model.admin.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bonvio.model.admin.User;
import com.bonvio.dao.admin.UserDao;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;


	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
    @Transactional
	public void addUser(User user) {

        user.setPassword(userDao.encode(user.getPassword()));
		userDao.addUser(user);
	}

	@Override
	public User findByUserName(String username) {
		return userDao.findByUserName(username);
	}

	@Override
	public String encode(String pass) {
		return userDao.encode(pass);
	}

    @Override
    @Transactional
    public void updateUser(User user) {

        User user1 = userDao.findByUserName(user.getUsername());
        if (!user1.getPassword().equals(user.getPassword())){
            user.setPassword(userDao.encode(user.getPassword()));
        }

        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void updateUserRoles(User user) {
        userDao.updateUserRoles(user);
    }

    @Override
    @Transactional
    public void updateUserRole(UserRole userRole) {
        userDao.updateUserRole(userRole);
    }

    @Override
    @Transactional
    public void createUser(User user) {


        user.setPassword(userDao.encode(user.getPassword()));
        userDao.addUser(user);


        userDao.createUser(user);
    }




}
