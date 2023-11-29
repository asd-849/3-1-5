//package ru.kata.spring.boot_security.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//import java.security.Principal;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//    private final UserService userService;
//    private final RoleService roleService;
//
//    @Autowired
//    public AdminController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @GetMapping()
//    public String showUsers(ModelMap model, Principal principal) {
//        model.addAttribute("users", userService.getAllUsers());
//        model.addAttribute("adminUser", userService.getUserByName(principal.getName()));
//        model.addAttribute("roles", roleService.getAllRoles());
//        model.addAttribute("newUser", new User());
//        return "admin";
//    }
//
//    @PostMapping("/new")
//    public String createUser(@ModelAttribute("user") User user) {
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/edit")
//    public String updateUser(@ModelAttribute("user") User user, @RequestParam Long id, Model model) {
//        model.addAttribute("user", userService.getUser(id));
//        userService.updateUserById(id, user);
//        return "redirect:/admin";
//    }
//
//    @DeleteMapping("/delete")
//    public String deleteUser(@RequestParam Long id) {
//        userService.removeUserById(id);
//        return "redirect:/admin";
//    }
//}
