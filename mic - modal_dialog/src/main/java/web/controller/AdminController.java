package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.SecurityRoles;
import web.model.User;
import web.service.SecurityRolesService;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private SecurityRolesService securityRolesService;

    @Autowired
    public void setUserService(UserService userService, SecurityRolesService securityRolesService){
        this.userService = userService;
        this.securityRolesService = securityRolesService;
    }

    @GetMapping()
    public String allUsers(Model model) {
        model.addAttribute("users", userService.userList());
        return "admin/admin";
    }

    @GetMapping(value = "/new")
    public String newUser(@ModelAttribute("user") User user, Model model){
        model.addAttribute("roleList", securityRolesService.getRoles());
        return "admin/new";
    }

    @PostMapping(value = "/addUser")
    public String addUser(@ModelAttribute("user") User user){
        for (SecurityRoles rle: user.getSecurityRolesList()) {
            Role role = new Role();
            role.setRole(rle.getRole());
            role.setRoles(rle);
            user.addRole(role);
        }
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("securityRoleList", securityRolesService.getRoles());
        return "admin/edit_modal";
    }

    @PatchMapping("/update")
    public String update(@ModelAttribute("user") User user){
        User userEdit = userService.getById(user.getId());
        userEdit.setSecurityRolesList(user.getSecurityRolesList());
        userEdit.setEmail(user.getEmail());
        userEdit.setLogin(user.getLogin());
        userEdit.setAge(user.getAge());
        userEdit.setPassword(user.getPassword());
        user=userEdit;
        List<Role> editRoleList = new ArrayList<>(user.getRoleList());
        for(Role oldRole: editRoleList){
            if(!user.getSecurityRolesList().contains(oldRole.getRoles())){
                user.removeRole(user.getRoleList().get(user.getRoleList().indexOf(oldRole)));
            }
        }
        for (SecurityRoles rle: user.getSecurityRolesList()) {
            if(!user.getRoleListString().contains(rle.getRole())) {
                Role role = new Role();
                role.setRole(rle.getRole());
                role.setRoles(rle);
                user.addRole(role);
            }
        }
        userService.edit(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable("id") long id){
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roleList", securityRolesService.getRoles());
        return "admin/delete_modal";
    }

    @DeleteMapping("/drop")
    public String drop(@ModelAttribute("user") User user){
        User user2 = userService.getById(user.getId());
        userService.delete(user2);
        return "redirect:/admin";
    }
}
