package com.fgoosev.Blog.Controllers;

import com.fgoosev.Blog.models.Role;
import com.fgoosev.Blog.models.User;
import com.fgoosev.Blog.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/reg")
    public String reg(Model model){
        return "reg";
    }

    @PostMapping("/reg")
    public String regUser(User user, Map<String, Object> models){
        User userFromDB = userRepository.findByLogin(user.getLogin());

        if(userFromDB != null){
            models.put("message", "user exists!");
            return "reg";
        }

        user.setAction(true);
        user.setRoles(Collections.singleton(Role.ADMIN));
        userRepository.save(user);
        return "redirect:/login";
    }
}
