package com.bonvio.controller;

import com.bonvio.model.admin.User;
import com.bonvio.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class HelloController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/redirect",method = RequestMethod.GET)
    public String redirect () {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUserName( authentication.getName());
        if(user.getRole().equals("Администратор")){
            return "redirect:/#/admin";
        }
        if(user.getRole().equals("Кладовщик")){
            return "redirect:/#/storekeeper";
        }
        if(user.getRole().equals("Колеровщик")){
            return "redirect:/#/assistant";
        }

        if(user.getRole().equals("Менеджер")){
            return "redirect:/#/manager";
        }

        if(user.getRole().equals("Ответственный менеджер")){
            return "redirect:/#/manager";
        }

        return "redirect:/";
    }



    @RequestMapping(value = "/getuser",method = RequestMethod.GET)
    @ResponseBody
    public User getUser () {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUserName( authentication.getName());

        userService.getAllUsers().get(0);
        return user;
    }


	@RequestMapping(method = RequestMethod.GET)
     public String printWelcome(ModelMap model) {
        //model.addAttribute("message", "Hello world! Order Manager!");
        return "index";
    }

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(ModelMap model) {
        //model.addAttribute("message", "Hello world! Order Manager!");
        return "hello";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error",
                    getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        }
        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");
        return model;
    }

    private String getErrorMessage(HttpServletRequest request, String key) {

        Exception exception = (Exception) request.getSession()
                .getAttribute(key);

        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username and password!";
        }
        return error;
    }

}