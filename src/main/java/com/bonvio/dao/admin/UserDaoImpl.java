package com.bonvio.dao.admin;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bonvio.dao.admin.UserDao;
import com.bonvio.model.admin.UserRole;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bonvio.model.admin.User;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public String encode(String pass) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        String result = encoder.encodePassword(pass, null);
        return result;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getAllUsers() {

        @SuppressWarnings("unchecked")
        //List<User> result = entityManager.createNamedQuery("selectAllUsers").getResultList();
        List<User> result = entityManager.createNativeQuery("select * from users ", User.class).getResultList();
        //System.out.println("result = "+ result.size());

        return result;
    }

    @Override
    public User findByUserName(String username) {
        User user = entityManager.find(User.class, username);
        return user;
    }

    @Override

    public void updateUserRoles(User userNew) {

        User user = entityManager.find(User.class, userNew.getUsername());
        user.setRole(userNew.getRole());

        for (UserRole userRole : user.getUserRoles()) {
            entityManager.remove(userRole);
        }
        entityManager.flush();

        Set<UserRole> userRoles = userNew.getUserRoles();

        for (UserRole userRole : userRoles) {
            userRole.setUser(user);
            entityManager.merge(userRole);
        }
    }

    @Override
    public void updateUserRole(UserRole userRole) {
        entityManager.merge(userRole);
    }

    @Override
    public void createUser(User user) {

        System.out.println(user);

        entityManager.persist(user);

        for (UserRole userRole : user.getUserRoles()) {

            UserRole userRole1 = new UserRole();
            userRole1.setTitle(userRole.getTitle());
            userRole1.setChecked(userRole.isChecked());
            userRole1.setRole(userRole.getRole());
            userRole1.setUser(user);

            entityManager.persist(userRole1);
        }

    }


}
