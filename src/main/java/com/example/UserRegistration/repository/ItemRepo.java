package com.example.UserRegistration.repository;

import com.example.UserRegistration.domain.Category;
import com.example.UserRegistration.domain.Item;
import com.example.UserRegistration.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {

    long countByUser_UsernameAndCreatedAtBetween(String username, LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Item> findByCategories_CategoryNameIn(List<String> categoryList);

//    @Query("SELECT i FROM Item i WHERE i.price = (SELECT MAX(ii.price) FROM Item ii WHERE :category MEMBER OF ii.categories) ORDER BY i.categories, i.price DESC")
//    List<Item> findMostExpensiveItemsInEachCategory(@Param("category") Category category);

    @Query("SELECT DISTINCT i FROM Item i " +
            "JOIN FETCH i.reviews r " +
            "WHERE i.user.username = :username " +
            "AND NOT EXISTS (SELECT 1 FROM Review r2 WHERE r2.item = i AND r2.rating NOT IN ('Excellent', 'Good'))")
    List<Item> findItemsWithPositiveCommentsByUser(@Param("username") String username);

    @Query(value = "SELECT i.username, COUNT(i.item_id) AS itemCount " +
            "FROM Item i " +
            "WHERE DATE(i.created_at) = :specificDate " +
            "GROUP BY i.username " +
            "HAVING COUNT(i.item_id) = (SELECT COUNT(i2.item_id) " +
            "FROM Item i2 " +
            "WHERE DATE(i2.created_at) = :specificDate " +
            "GROUP BY i2.username " +
            "ORDER BY COUNT(i2.item_id) DESC " +
            "LIMIT 1)", nativeQuery = true)
    List<Object[]> findUsersWithMostItemsOnDate(@Param("specificDate") LocalDate specificDate);
}
