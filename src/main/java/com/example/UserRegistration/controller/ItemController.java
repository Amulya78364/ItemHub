package com.example.UserRegistration.controller;

import com.example.UserRegistration.domain.Category;
import com.example.UserRegistration.domain.Item;
import com.example.UserRegistration.repository.CategoryRepo;
import com.example.UserRegistration.repository.ItemRepo;
import com.example.UserRegistration.repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("/search")
    public String showSearchPage() {
        return "search";
    }

    @GetMapping("/searchItems")
    public String searchItems(@RequestParam String category, Model model) {

        List<String> categoryList = Arrays.asList(category.split(",")); // Split the input string into a list

        // Find items by category name
        List<Item> items = itemRepo.findByCategories_CategoryNameIn(categoryList);

        // Add the list of items to the model for display in the view
        model.addAttribute("items", items);

        return "searchResults";
    }

    @GetMapping("/insertItem")
    public String showInsertItemPage() {
        return "insertItem";
    }

    @PostMapping("/insertItem")
    public ModelAndView insertItem(@ModelAttribute("item") Item item, @RequestParam("category") String categoryInput, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("insertItem");

        // Get the username from the session attribute
        String username = (String) session.getAttribute("username");

        // Calculate the start and end times of the day
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        // Count the number of items posted by the user today
        long itemCountToday = itemRepo.countByUser_UsernameAndCreatedAtBetween(username, startOfDay, endOfDay);

        if (itemCountToday >= 3) {
            modelAndView.addObject("errorMessage", "You have exceeded the daily item posting limit.");
        } else {
            // Split the comma-separated categories and convert them to a list
            List<String> categoryList = Arrays.asList(categoryInput.split(","));
            List<Category> categories = new ArrayList<>();

            for (String categoryName : categoryList) {
                if (categoryName != null && !categoryName.trim().isEmpty()) {
                    Category existingCategory = categoryRepo.findByCategoryName(categoryName.trim());
                    if (existingCategory == null) {
                        // Category doesn't exist, create and save it
                        Category newCategory = new Category(categoryName.trim());
                        categoryRepo.save(newCategory);
                        categories.add(newCategory);
                    } else {
                        // Category already exists, use the existing one
                        categories.add(existingCategory);
                    }
                }
            }

            item.setCategories(categories);

            // Set the user for the item
            item.setUser(userRepo.findByUsername(username));
            item.setCreatedAt(startOfDay);

            // Save the item to the database
            itemRepo.save(item);
            modelAndView.addObject("successMessage", "Item inserted successfully");
        }

        return modelAndView;
    }


    @GetMapping("/displayItem")
    public String displayItem(@RequestParam Long itemId, Model model) {
        // Retrieve the item by ID from the repository
        Optional<Item> optionalItem = itemRepo.findById(itemId);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            System.out.println(item.getItemId());
            // Add the item to the model for display on the "displayItem" page
            model.addAttribute("item", item);
            return "displayItem"; // Create a "displayItem" HTML page to display item details
        } else {
            // Handle the case where the item with the provided ID is not found
            // You can redirect to an error page or perform other error handling
            return "itemNotFound"; // Create an "itemNotFound" HTML page for displaying an error message
        }
    }

}
