package com.example.UserRegistration.controller;

import com.example.UserRegistration.domain.Favorite;
import com.example.UserRegistration.domain.FavoriteForm;
import com.example.UserRegistration.domain.FavoriteListForm;
import com.example.UserRegistration.domain.User;
import com.example.UserRegistration.repository.FavoriteRepo;
import com.example.UserRegistration.repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class FavoriteUserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FavoriteRepo favoriteRepo;

    private Set<String> markedFavorites = new HashSet<>(); // To store marked favorites, assuming usernames are unique

    @GetMapping("/markFavorite")
    public String showMarkFavoriteForm(Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        model.addAttribute("favoriteForm", new FavoriteForm());
        return "markFavorite";
    }

    @PostMapping("/markFavorite")
    public String markFavorite(@ModelAttribute FavoriteForm favoriteForm, HttpSession session) {
        String loggedInUsername = (String) session.getAttribute("username");
        String selectedUsername = favoriteForm.getUsername();

        User loggedInUser = userRepo.findByUsername(loggedInUsername);
        User selectedUser = userRepo.findByUsername(selectedUsername);

        // Logic to mark selected user as favorite for the logged-in user
        Favorite favorite = new Favorite();
        favorite.setUser(loggedInUser);
        favorite.setFavoriteUser(selectedUser);
        favoriteRepo.save(favorite);

        return "redirect:/markFavorite";
    }

    @GetMapping("/listFavorites")
    public String showListFavoritesForm(Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        model.addAttribute("favoriteListForm", new FavoriteListForm());
        return "listFavorites";
    }

    @PostMapping("/listFavorites")
    public String listFavorites(@ModelAttribute FavoriteListForm favoriteListForm, Model model) {
        String username1 = favoriteListForm.getUsername1();
        String username2 = favoriteListForm.getUsername2();

        // Logic to find common favorites for username1 and username2
        List<User> commonFavorites = favoriteRepo.findCommonFavorites(username1, username2);

        model.addAttribute("commonFavorites", commonFavorites);
        return "resultFavorites";
    }

}
