package com.example.demo.controller;

import com.example.demo.model.User;

import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final String USER_TABLE = "users/userTable :: user_list";
    private static final String ERROR_ALERT = "fragments/alert :: alert" ;
    private static final String EDIT_USER = "users/modal/editUser";
    private static final String ADD_MODAL = "users/modal/addUser";

    /* @GetMapping("/users")
      public String getUsers(Model model){
          model.addAttribute("users",new ArrayList<>());
          return "users/user";
      }*/

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/addNewUser")
    public String addUser(Model model) {
        try {
            model.addAttribute("predefinedRoles", userService.getAllRoles());
            model.addAttribute("user", new User());
            return "users/modal/addUser";
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "users/modal/addUser";
        }
    }

    @PostMapping("/saveUser")
    public String saveUser(User user, Model model) {
        try {
            userService.createNewUser(user);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "Пользователь успешно добавлен");
            model.addAttribute("alertClass", "alert-success");
            return "users/userTable :: user_list ";
        } catch (Exception e) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "Ошибка добавление пользователя");
            model.addAttribute("alertClass", "alert-danger");
            return "users/userTable :: user_list ";
        }

    }

    @GetMapping("/edit")
    public String editUser(Long pid, Model model) {
        try {
            User user = userService.getUserById(pid);
            model.addAttribute("predefinedRoles", userService.getAllRoles());
            model.addAttribute("user", user);
            return "users/modal/editUser";
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "users/modal/editUser";
        }
    }

    @PostMapping("/updateUser")
    public String updateUser(User user, Model model) {
        try {
            userService.updateUser(user);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "Пользователь успешно изменен");
            model.addAttribute("alertClass", "alert-success");
            return "users/userTable :: user_list";
        } catch (Exception e) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "Ошибка изменения пользователя");
            model.addAttribute("alertClass", "alert-danger");
          return "users/userTable :: user_list";
        }
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteUser(Long pid, Model model) {
        try {
            userService.deleteUserById(pid);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "Пользователь успешно удален");
            model.addAttribute("alertClass", "alert-success");
            return "users/userTable :: user_list";
        } catch (Exception e) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "Ошибка удаления пользователя");
            model.addAttribute("alertClass", "alert-danger");
            return "users/userTable :: user_list";
        }
    }

    @GetMapping("/filter")
    public String filter(String s, Model model) {
        try {
            List<User> users = userService.filter(s);
            model.addAttribute("users", users);
            return "users/userTable :: user_list";
        } catch (Exception e) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "При работе с пользователями произошла ошибка");
            model.addAttribute("alertClass", "alert-danger");
            return "users/userTable :: user_list";
        }
    }
    @GetMapping("/checkLogin")
    public String chekLogin (@RequestParam("login") String login, Model model, HttpServletResponse response) {
        if(userService.getUserByLogin(login).isPresent()){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            model.addAttribute("message", "Пользователь с таким пользователем уже существует");
            model.addAttribute("alertClass","alert-danger");
        }
        return ERROR_ALERT;
    }
}
