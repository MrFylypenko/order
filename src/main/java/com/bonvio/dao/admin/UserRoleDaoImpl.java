package com.bonvio.dao.admin;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bonvio.model.admin.Groups;
import com.bonvio.model.admin.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bonvio.model.admin.UserRole;

import java.util.List;

@Repository
public class UserRoleDaoImpl implements UserRoleDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void addUserRole(UserRole userRole) {
		entityManager.persist(userRole);
		
	}

    @Override
    public List<Role> getRoles() {
        return entityManager.createNativeQuery("select * from role", Role.class).getResultList();
    }


    @Override
    public List<Groups> getGroups() {
        return entityManager.createNativeQuery("select * from groups", Groups.class).getResultList();
    }

    @Override
    public void updateRole(Role role) {
        Role role2 = entityManager.find(Role.class, role.getId());
        role.setGroups(role2.getGroups());
        entityManager.merge(role);
    }


}
