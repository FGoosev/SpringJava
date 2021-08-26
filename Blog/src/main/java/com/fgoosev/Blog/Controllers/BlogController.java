package com.fgoosev.Blog.Controllers;

import com.fgoosev.Blog.models.Post;
import com.fgoosev.Blog.models.User;
import com.fgoosev.Blog.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blog(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPost(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model, @AuthenticationPrincipal User user){
        Post post = new Post(title, anons, full_text, user);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model){

        if(!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);

        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String edit(@PathVariable(value = "id") long id, Model model){

        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }

            Optional<Post> post = postRepository.findById(id);
            ArrayList<Post> res = new ArrayList<>();
            post.ifPresent(res::add);
            model.addAttribute("post", res);
            return "blog-edit";

    }

    @PostMapping("/blog/{id}/edit")
    public String blogUpdate(@AuthenticationPrincipal User user,@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        if(post.getAuthorId() == user.getId()) {
            post.setTitle(title);
            post.setAnons(anons);
            post.setFull_text(full_text);
            postRepository.save(post);
            return "redirect:/blog";
        }else return "redirect:/error";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogDelete(@AuthenticationPrincipal User user, @PathVariable(value = "id") long id,Model model){
        Post post = postRepository.findById(id).orElseThrow();
        if(post.getAuthorId() == user.getId()) {
            postRepository.delete(post);
            return "redirect:/blog";
        }else return "redirect:/error";
    }

    @PostMapping("filter")
    public String filterByTitle(@RequestParam String title, Model model){
        List<Post> res = postRepository.findByTitle(title);
        model.addAttribute("posts",res);
        return "/blog";
    }
}
