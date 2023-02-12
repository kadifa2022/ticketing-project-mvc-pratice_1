package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final RoleService roleService;//dependency Injection
    private final UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/create")//form for userCreate ls
    public String createUser(Model model){
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", roleService.findAll()); //from db go to service and impl create map
        model.addAttribute("users", userService.findAll());

        return "/user/create";
    }
    @PostMapping("/create")//for save button//bring the user object and save
    public String insertUser(@ModelAttribute("user") UserDTO user){
        //go to html. and find what you need -user object-roles-users
       // model.addAttribute("user", new UserDTO());//to pass one empty object
       // model.addAttribute("roles", roleService.findAll());//for role
        userService.save(user);
       // model.addAttribute("users", userService.findAll());//for all users
        return "redirect:/user/create";          //what ever view requesting provide and then we need to save
    }

    //part 3 starts here
    @GetMapping("/update/{username}")
    public String editUser(@PathVariable("username")String username, Model model){   //from outside                       //user object{}, roles{}, users{}
        //user object ${user}
        model.addAttribute("user", userService.findById(username));
        //roles${roles}
        model.addAttribute("roles", roleService.findAll());
        //users ${users}
        model.addAttribute("users", userService.findAll());

            return "/user/update";
        }
        @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") UserDTO user){//empty form
          //need to update user see service
            userService.update(user);

            return " redirect:/user/create";
        }

        @GetMapping("/delete/{username}")
        public String deleteUser(@PathVariable("username")String username){
        userService.deleteById(username);

        return "redirect:/user/create";


        }






}
