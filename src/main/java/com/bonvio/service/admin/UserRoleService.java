package com.bonvio.service.admin;

import com.bonvio.model.admin.Groups;
import com.bonvio.model.admin.Role;
import com.bonvio.model.admin.UserRole;

import java.util.List;

public interface UserRoleService {
	
	public void addUserRole(UserRole userRole);
    public List<Role> getRoles();
    public List<Groups> getGroups();
    public void updateRole(Role role);

}
