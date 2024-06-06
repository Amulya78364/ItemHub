package com.example.UserRegistration.controller;

import com.example.UserRegistration.domain.Item;
import com.example.UserRegistration.domain.Review;
import com.example.UserRegistration.domain.User;
import com.example.UserRegistration.repository.ItemRepo;
import com.example.UserRegistration.repository.ReviewRepo;
import com.example.UserRegistration.repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class ReviewController {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/submitReview")
    public String showDisplayItemsPage(Review review) {
        return "displayItems";
    }

    @PostMapping("/submitReview")
    public ModelAndView submitReview(@RequestParam Long itemId, @RequestParam String rating, @RequestParam String description, RedirectAttributes attributes, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("submitReview");

        // Get the username of the currently logged-in user
        String username = (String) session.getAttribute("username");

        // Find the item by its ID
        Optional<Item> optionalItem = itemRepo.findById(itemId);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();

            // Check if the user is reviewing their own item
            if (item.getUser().getUsername().equals(username)) {
                modelAndView.addObject("errorMessage", "You cannot review your own item.");
            } else {
                // Calculate the start and end times of the day
                LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
                LocalDateTime endOfDay = startOfDay.plusDays(1);

                // Count the number of reviews posted by the user today
                long reviewCountToday = reviewRepo.countByUser_UsernameAndCreatedAtBetween(username, startOfDay, endOfDay);

                if (reviewCountToday >= 3) {
                    modelAndView.addObject("errorMessage", "You have exceeded the daily review posting limit.");
                } else {
                    // Create a new review
                    Review review = new Review();
                    review.setItem(item);
                    review.setRating(rating);
                    review.setDescription(description);
                    review.setCreatedAt(startOfDay);
                    review.setUser(userRepo.findByUsername(username)); // Set the user who posted the review

                    // Add the review to the item and save them both
                    item.getReviews().add(review);
                    itemRepo.save(item);

                    modelAndView.addObject("successMessage", "Review submitted successfully");
                }
            }
        } else {
            modelAndView.addObject("errorMessage", "Item not found");
        }

        return modelAndView;
    }

}
