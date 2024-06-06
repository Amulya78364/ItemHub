package com.example.UserRegistration.controller;

import com.example.UserRegistration.domain.User;
import com.example.UserRegistration.repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/register")
    public String showRegisterPage(){
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView register(User user) {
        ModelAndView modelAndView = new ModelAndView("register");
        List<User> data = userRepo.findAll();
        for (User u : data) {
            String name = u.getUsername();
            if (name.equals(user.getUsername())) {
                modelAndView.addObject("message", "User already exists");
                modelAndView.addObject("loginLink", "/login"); // Add a link to the login page
                return modelAndView;

            }
        }
        // Check for duplicate email here (custom validation)
        User existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser != null) {
            modelAndView.addObject("message", "Email already exists");
            return modelAndView;
        }
        userRepo.save(user);
        modelAndView.addObject("message", "User registered successfully");
        return modelAndView;
    }

    @GetMapping("/login")
    public String showLoginPage(User user) {
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, RedirectAttributes attributes, HttpSession session) {
        List<User> data = userRepo.findAll();
        boolean isLoginSuccessful = false;

        for (User u : data) {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                isLoginSuccessful = true;

                // Store the username in a session attribute
                session.setAttribute("username", user.getUsername());

                break;
            }
        }

        if (isLoginSuccessful) {
            attributes.addFlashAttribute("message", "Login Successful");
            return "redirect:/loginSuccess";
        } else {
            attributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }
    }

    @GetMapping("/loginSuccess")
    public ModelAndView showLoginSuccessPage() {
        return new ModelAndView("userOptions");
    }

    @GetMapping("/userOptions")
    public ModelAndView showUserOptionsPage() {
        return new ModelAndView("userOptions");
    }
}
