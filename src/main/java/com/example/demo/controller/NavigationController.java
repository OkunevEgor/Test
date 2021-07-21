package com.example.demo.controller;

import com.example.demo.model.User;

import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NavigationController {

    @Autowired
    UserServiceImpl userServiceIml;

    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN')")


    public String users(Model model) {
        List<User> list = userServiceIml.getAllUsers();
        model.addAttribute("users", list);
        return "users/user";
    }

    @GetMapping("/references")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARY')")
    public String references() {
        return "references/references";
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String statistics() {
        return "statistics";
    }

    @GetMapping("/index")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getBookForUser() {
        return "index";

    }
}
