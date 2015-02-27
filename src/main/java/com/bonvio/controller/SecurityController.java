package com.bonvio.controller;

import com.bonvio.model.admin.Groups;
import com.bonvio.service.admin.MyUserDetailsService;
import com.bonvio.service.admin.UserRoleService;
import com.bonvio.service.admin.UserService;
import com.bonvio.model.admin.Role;
import com.bonvio.model.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 09.02.2015.
 */

@Controller
@RequestMapping(value = "/admin")
public class SecurityController {

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    UserService userService;

    @Autowired
    private SessionRegistry sessionRegistry;


    @Autowired
    MyUserDetailsService myUserDetailsService;


    @RequestMapping(method = RequestMethod.GET)
    public String admin() {
      /*  ModelAndView model = new ModelAndView();
        model.setViewName("admin");*/
        return "admin";
    }



    @RequestMapping(value = "/getroles", method = RequestMethod.GET)
    @ResponseBody
    public List<Role> getRoles() {
        return userRoleService.getRoles();
    }


    @RequestMapping(value = "/getgroups", method = RequestMethod.GET)
    @ResponseBody
    public List<Groups> getGroups() {
        return userRoleService.getGroups();
    }


    @RequestMapping(value = "/getusers", method = RequestMethod.POST)
    @ResponseBody
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/updateuserroles", method = RequestMethod.POST)
    @ResponseBody
    public String changeUserRoles(@RequestBody User user) {

        userService.updateUserRoles(user);

        System.out.println(sessionRegistry.getAllPrincipals().size());

        //sessionRegistry.registerNewSession();



    String g;


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authoritiesOld = new ArrayList<GrantedAuthority>(auth.getAuthorities());

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(myUserDetailsService.loadUserByUsername(user.getUsername()).getAuthorities());

        //authorities.add(new SimpleGrantedAuthority("ROLE_NEWROLE"));

        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(),auth.getCredentials(),authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);




        return "1";
    }

    @RequestMapping(value = "/createuser", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestBody User user) {
        userService.createUser(user);
        return "1";
    }

    @RequestMapping(value = "/updategrouprole", method = RequestMethod.POST)
    @ResponseBody
    public String updateGroupRoles (@RequestBody Role role) {
        userRoleService.updateRole(role);
        return "1";
    }

}
