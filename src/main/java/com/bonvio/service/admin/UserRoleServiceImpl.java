package com.bonvio.service.admin;

import com.bonvio.model.admin.Groups;
import com.bonvio.model.admin.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bonvio.model.admin.UserRole;
import com.bonvio.dao.admin.UserRoleDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	UserRoleDao userRoleDao;

	@Override
	public void addUserRole(UserRole userRole) {
		userRoleDao.addUserRole(userRole);
	}

    @Override
    public List<Role> getRoles() {
        return userRoleDao.getRoles();
    }

    @Override
    public List<Groups> getGroups() {
        return userRoleDao.getGroups();
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        userRoleDao.updateRole(role);
    }

}
