package com.fgoosev.Blog.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("title", "Главная страница");
        return "homePage";
    }
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("title", "Окно авторизации");
        return "login";
    }
}
