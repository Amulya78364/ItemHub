package com.example.UserRegistration.controller;

import com.example.UserRegistration.domain.Category;
import com.example.UserRegistration.domain.Item;
import com.example.UserRegistration.domain.Review;
import com.example.UserRegistration.domain.User;
import com.example.UserRegistration.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.UserRegistration.repository.CategoryRepo;
import com.example.UserRegistration.repository.ItemRepo;
import com.example.UserRegistration.repository.UserRepo;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
public class DatabaseInitializationController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ReviewRepo reviewRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("/initializeDatabase")
    public String initializeDatabase(Model model) {
        // Implement database initialization and data population logic here

        // Sample initialization for demonstration purposes
        initializeSampleData();

        // Provide a message to indicate the completion of initialization
        model.addAttribute("message", "Database initialization completed.");

        return "databaseInitialized"; // Create an HTML page to display the initialization message
    }

    private void initializeSampleData() {

        //Create and save sample Users
        User user1 = new User();
        user1.setUsername("john_doe");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPassword("password1");
        user1.setEmail("john.doe@example.com");

        User user2 = new User();
        user2.setUsername("alice_smith");
        user2.setFirstName("Alice");
        user2.setLastName("Smith");
        user2.setPassword("password2");
        user2.setEmail("alice.smith@example.com");

        User user3 = new User();
        user3.setUsername("bob_johnson");
        user3.setFirstName("Bob");
        user3.setLastName("Johnson");
        user3.setPassword("bob1234");
        user3.setEmail("bob.johnson@example.com");

        User user4 = new User();
        user4.setUsername("sarah_wilson");
        user4.setFirstName("Sarah");
        user4.setLastName("Wilson");
        user4.setPassword("sarahPass");
        user4.setEmail("sarah.wilson@example.com");

        User user5 = new User();
        user5.setUsername("david_miller");
        user5.setFirstName("David");
        user5.setLastName("Miller");
        user5.setPassword("davidPass");
        user5.setEmail("david.miller@example.com");

        userRepo.saveAll(Arrays.asList(user1, user2, user3, user4, user5));

        // Create and save sample categories
        Category category1 = new Category("Electronics");
        Category category2 = new Category("Clothing");
        Category category3 = new Category("Books");
        Category category4 = new Category("Furniture");
        Category category5 = new Category("Sports");

        categoryRepo.saveAll(Arrays.asList(category1, category2, category3, category4, category5));

        List<Category> electronicsCategoryList = Arrays.asList(category1);
        List<Category> clothingCategoryList = Arrays.asList(category2);
        List<Category> booksCategoryList = Arrays.asList(category3);
        List<Category> furnitureCategoryList = Arrays.asList(category4);
        List<Category> sportsCategoryList = Arrays.asList(category5);

        // Create and save sample items
        Item item1 = new Item();
        item1.setTitle("Smartphone");
        item1.setDescription("High-end smartphone");
        item1.setCategories(electronicsCategoryList);
        item1.setPrice(799.99);
        item1.setUser(user1);
        item1.setCreatedAt(LocalDateTime.now());

        Item item2 = new Item();
        item2.setTitle("T-shirt");
        item2.setDescription("Cotton t-shirt");
        item2.setCategories(clothingCategoryList);
        item2.setPrice(29.99);
        item2.setUser(user2);
        item2.setCreatedAt(LocalDateTime.now());

        Item item3 = new Item();
        item3.setTitle("The Great Gatsby");
        item3.setDescription("Classic novel by F. Scott Fitzgerald");
        item3.setCategories(booksCategoryList);
        item3.setPrice(12.99);
        item3.setUser(user3);
        item3.setCreatedAt(LocalDateTime.now());

        Item item4 = new Item();
        item4.setTitle("Coffee Table");
        item4.setDescription("Modern coffee table for your living room");
        item4.setCategories(furnitureCategoryList);
        item4.setPrice(129.99);
        item4.setUser(user4);
        item4.setCreatedAt(LocalDateTime.now());

        Item item5 = new Item();
        item5.setTitle("Yoga Mat");
        item5.setDescription("High-quality yoga mat for fitness enthusiasts");
        item5.setCategories(sportsCategoryList);
        item5.setPrice(29.99);
        item5.setUser(user5);
        item5.setCreatedAt(LocalDateTime.now());

        itemRepo.saveAll(Arrays.asList(item1, item2, item3, item4, item5));

        // Create and save sample reviews
        Review review1 = new Review();
        review1.setItem(item1);
        review1.setRating("excellent");
        review1.setDescription("Great smartphone!");
        review1.setCreatedAt(LocalDateTime.now());
        review1.setUser(user2);

        Review review2 = new Review();
        review2.setItem(item2);
        review2.setRating("good");
        review2.setDescription("Nice t-shirt.");
        review2.setCreatedAt(LocalDateTime.now());
        review2.setUser(user1);

        Review review3 = new Review();
        review3.setItem(item3);
        review3.setRating("Fair");
        review3.setDescription("Interesting plot but somewhat hard to follow.");
        review3.setCreatedAt(LocalDateTime.now());
        review3.setUser(user3);

        Review review4 = new Review();
        review4.setItem(item4);
        review4.setRating("excellent");
        review4.setDescription("Stylish and sturdy coffee table.");
        review4.setCreatedAt(LocalDateTime.now());
        review4.setUser(user4);

        Review review5 = new Review();
        review5.setItem(item5);
        review5.setRating("Good");
        review5.setDescription("Quality yoga mat for my daily workouts.");
        review5.setCreatedAt(LocalDateTime.now());
        review5.setUser(user5);

        reviewRepo.saveAll(Arrays.asList(review1, review2, review3, review4, review5));
    }
}

