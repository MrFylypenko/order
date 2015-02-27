package com.bonvio.service.admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bonvio.model.admin.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bonvio.dao.admin.UserDao;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {

		com.bonvio.model.admin.User user = userDao.findByUserName(username);

        //System.out.println(username + "Пробуем искать этого пользователя");

        if ((username.equals("admin")) && (user == null)){

            System.out.println(username + "Добавляем  пользователя  админ");
            user = new com.bonvio.model.admin.User();
            user.setUsername("admin");
            user.setPassword(userService.encode("12345"));
            userService.addUser(user);

            UserRole userRole = new UserRole();
            userRole.setRole("ROLE_USER");
            userRole.setUser(user);
            UserRole userRole2 = new UserRole();
            userRole2.setRole("ROLE_ADMIN");
            userRole2.setUser(user);

  /*          Set<UserRole> userRoleList = new HashSet<UserRole>();
            user.setUserRole(userRoleList);
            user.setUserRole(userRoleList);*/

            userRoleService.addUserRole(userRole);
            userRoleService.addUserRole(userRole2);
        }

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		List<GrantedAuthority> authorities = buildUserAuthority(user
				.getUserRoles());

		return buildUserForAuthentication(user, authorities);

	}

	private User buildUserForAuthentication(com.bonvio.model.admin.User user,
			List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(),
				true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(
			Set<UserRole> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(
				setAuths);

		return result;
	}

}