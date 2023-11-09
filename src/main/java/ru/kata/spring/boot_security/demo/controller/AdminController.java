package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index() {
        return "index";
    }
    @GetMapping("admin")
    public String adminPage(ModelMap model) {
        model.addAttribute("adminUser", userService.getAllUsers());
        return "admin";
    }
    @GetMapping("/new")
    public String newUser(Model model, Model role) {
        model.addAttribute("user", new User());
        role.addAttribute("rolesList", roleService.getAllRoles());
        return "new";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/admin";
    }

    @GetMapping("/edit")
    public String editUser(Model model, @RequestParam Long id, Model role) {
//        model.addAttribute("user", userService.getUser(id));
        role.addAttribute("rolesList", roleService.getAllRoles());
        var user = userService.getUser(id);
        user.setPassword(null);
        model.addAttribute("user", user);
        return "/edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam Long id) {
        userService.updateUserById(id, user);
        return "redirect:/admin/admin";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.removeUserById(id);
        return "redirect:/admin/admin";
    }
}
