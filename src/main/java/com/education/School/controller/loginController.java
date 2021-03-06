package com.education.School.controller;

//LOGIN/LOGOUT CONTROLLER CLASS
import com.education.School.model.Person;
import com.education.School.repository.personRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Controller
public class loginController {
    //'/login' is both a GET and POST request

    @Autowired
    private personRepository personRepo;
    @RequestMapping(value ="/login", method = {RequestMethod.GET,RequestMethod.POST})
    public String displayLoginPage(@RequestParam(value = "error",required = false) String error ,
                                   @RequestParam(value ="logout",required = false) String logout,
                                   @RequestParam(value ="register",required = false) String register,
                                   @RequestParam(value ="verify",required = false) String verify,Model model){
        String errorMsg = null;
        String registered = null;
        String verified = null;
        if(error!=null){
            errorMsg = "Username or Password is incorrect!";
        }
        if(logout!=null){
            errorMsg = "You have been successfully logged out!";
        }
        if(register!=null){
            registered ="You have been registered,verify mail to complete the process!!!";
        }
        if(verify!=null){
            verified = "Email verified successfully!!!";
        }
        model.addAttribute("errorMsg" , errorMsg);
        model.addAttribute("registered" , registered);
        model.addAttribute("verified",verified);

        return "login.html";
    }

    //LOGOUT Function

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request , HttpServletResponse response){
        //Getting the authenticating details of the user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null){
            //If user is already authenticated then call the logout function to log him out
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }
        //As soon as user is logged out LoginSuccessURL is triggered in ProjectSecurity class
        return "redirect:/login?logout=true";
    }
}
