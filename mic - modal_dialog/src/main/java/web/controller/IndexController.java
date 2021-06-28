package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.security.Principal;

@Controller
public class IndexController {
private UserService userService;

@Autowired
public void setUserService(UserService userService){
    this.userService = userService;
}

    @GetMapping("/")
    public String indexPage(Model model, Principal principal) {
        model.addAttribute("user", userService.getByLogin(principal.getName()));
        return "user/getById";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "page/login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "page/login";
    }

    @GetMapping(value = "/new")
    public String newUser(@ModelAttribute("user") User user){
        return "page/new";
    }

    @PostMapping(value = "/addUser")
    public String addUser(@ModelAttribute("user") User user, @ModelAttribute("role") Role role){
        role.setRole("USER");
        user.addRole(role);
        userService.add(user);
        return "redirect:/login";
    }



}
