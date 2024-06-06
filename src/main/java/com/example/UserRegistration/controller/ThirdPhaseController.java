package com.example.UserRegistration.controller;

import com.example.UserRegistration.domain.Category;
import com.example.UserRegistration.domain.Item;
import com.example.UserRegistration.domain.User;
import com.example.UserRegistration.repository.CategoryRepo;
import com.example.UserRegistration.repository.ItemRepo;
import com.example.UserRegistration.repository.ReviewRepo;
import com.example.UserRegistration.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ThirdPhaseController {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ReviewRepo reviewRepo;

//    @GetMapping("/mostExpensiveItems")
//    public String mostExpensiveItems(Model model) {
////        List<Item> mostExpensiveItems = itemRepo.findMostExpensiveItemsInEachCategory();
////
////        // Add the result to the model
////        model.addAttribute("mostExpensiveItems", mostExpensiveItems);
////
////        // Return the HTML view name
////        return "mostExpensiveItems";
//
//        List<Category> categories = categoryRepo.findAll();
//        List<Item> mostExpensiveItems = new ArrayList<>();
//
//        for (Category category : categories) {
//            List<Item> expensiveItems = itemRepo.findMostExpensiveItemsInEachCategory(category);
//            if (expensiveItems != null && !expensiveItems.isEmpty()) {
//                mostExpensiveItems.add(expensiveItems.get(0)); // Add the most expensive item for each category
//            }
//        }
//
//        // Add the result to the model
//        model.addAttribute("mostExpensiveItems", mostExpensiveItems);
//
//        // Return the HTML view name
//        return "mostExpensiveItems";
//    }

    @GetMapping("/mostExpensiveItems")
    public String findMostExpensiveItems(Model model) {
        List<Item> allItems = itemRepo.findAll();
        Map<Category, Item> mostExpensiveItemsByCategory = findMostExpensiveItems(allItems);

        model.addAttribute("mostExpensiveItemsByCategory", mostExpensiveItemsByCategory);

        return "mostExpensiveItems";
    }

    private Map<Category, Item> findMostExpensiveItems(List<Item> items) {
        Map<Category, Item> mostExpensiveItemsByCategory = items.stream()
                .flatMap(item -> item.getCategories().stream().map(category -> new AbstractMap.SimpleEntry<>(category, item)))
                .collect(Collectors.groupingBy(
                        entry -> entry.getKey(),
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingDouble(entry -> entry.getValue().getPrice())),
                                entry -> entry.map(Map.Entry::getValue).orElse(null)
                        )
                ));

        return mostExpensiveItemsByCategory;
    }

    @GetMapping("/usersPostingOnSameDay")
    public String showForm() {
        return "usersPostingOnSameDay";
    }

    @PostMapping("/usersPostingOnSameDay")
    public String getUsersWithTwoItemsOnSameDayAndCategories(
            @RequestParam("categoryX") String categoryX,
            @RequestParam("categoryY") String categoryY,
            Model model) {

        List<String> users = userRepo.findUsersWithTwoItemsOnSameDayAndCategories(categoryX, categoryY);
        model.addAttribute("users", users);

        return "usersPostingOnSameDayResults";
    }

    @GetMapping("/itemsWithExcellentOrGoodComments")
    public String showItemsWithExcellentOrGoodComments(Model model) {
        List<String> users = userRepo.findAllDistinctUsernames();
        model.addAttribute("users", users);
        return "itemsWithExcellentOrGoodComments";
    }

    @PostMapping("/itemsWithExcellentOrGoodComments")
    public String itemsWithExcellentOrGoodComments(@RequestParam("username") String username, RedirectAttributes attributes) {
        List<String> users = userRepo.findAllDistinctUsernames();
        attributes.addFlashAttribute("users", users);

        List<Item> items = itemRepo.findItemsWithPositiveCommentsByUser(username);
        attributes.addFlashAttribute("items", items);

        return "redirect:/itemsWithExcellentOrGoodComments";
    }

    @GetMapping("/userWithMostItemsOnDate")
    public String showMostItemsOnDateForm() {
        return "userWithMostItemsOnDate";
    }

    @PostMapping("/userWithMostItemsOnDate")
    public String searchUsersWithMostItemsOnDate(@RequestParam("specificDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate specificDate, Model model) {
        List<Object[]> usersWithMostItems = itemRepo.findUsersWithMostItemsOnDate(specificDate);

        // Extract usernames and item counts
        List<String> usernames = new ArrayList<>();
        List<Long> itemCounts = new ArrayList<>();
        for (Object[] result : usersWithMostItems) {
            usernames.add((String) result[0]);
            itemCounts.add((Long) result[1]);
        }

        model.addAttribute("usernames", usernames);
        model.addAttribute("itemCounts", itemCounts);

        return "mostItemsOnDateResults";
    }

    @GetMapping("/usersNeverPostedExcellentItems")
    public String showUsersNeverPostedExcellentItems(Model model) {
        List<User> users = userRepo.findUsersWhoNeverPostedExcellentItems();
        model.addAttribute("users", users);
        return "usersNeverPostedExcellentItems";
    }

    @GetMapping("/usersWithoutPoorReviews")
    public String showUsersWithoutPoorReviews(Model model) {
        List<User> usersWithoutPoorReviews = userRepo.findUsersWithoutPoorReviews();
        model.addAttribute("users", usersWithoutPoorReviews);
        return "usersWithoutPoorReviews";
    }

    @GetMapping("/usersWithAllPoorReviews")
    public String showUsersWithAllPoorReviews(Model model) {
        List<User> usersWithAllPoorReviews = userRepo.findUsersWithAllPoorReviews();
        model.addAttribute("users", usersWithAllPoorReviews);
        return "usersWithAllPoorReviews";
    }

    @GetMapping("/usersNoPoorReviews")
    public String getUsersWithNoPoorReviews(Model model) {
        List<User> users = userRepo.findUsersWithItemsNeverReceivedPoorReviews();
        model.addAttribute("users", users);
        return "usersNoPoorReviews";
    }

    @GetMapping("/userPairsWithExcellentReviews")
    public String getUserPairsWithExcellentReviews(Model model) {
        List<User> userPairs = userRepo.findUserPairsWithExcellentReviews();
        model.addAttribute("userPairs", userPairs);

        return "userPairsWithExcellentReviews";
    }
}

