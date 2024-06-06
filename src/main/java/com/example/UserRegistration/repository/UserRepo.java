package com.example.UserRegistration.repository;

import com.example.UserRegistration.domain.Category;
import com.example.UserRegistration.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

   User findByEmail(String email);

    User findByUsername(String username);

    @Query(value= "SELECT u.username " +
            "FROM User u " +
            "JOIN Item i1 ON u.username = i1.username " +
            "JOIN Item i2 ON u.username = i2.username " +
            "JOIN item_category ic1 ON i1.item_id = ic1.item_id " +
            "JOIN item_category ic2 ON i2.item_id = ic2.item_id " +
            "JOIN Category c1 ON ic1.category_id = c1.category_id " +
            "JOIN Category c2 ON ic2.category_id = c2.category_id " +
            "WHERE i1.created_at = i2.created_at " +
            "AND i1.item_id <> i2.item_id " +
            "AND c1.category_name = :categoryX " +
            "AND c2.category_name = :categoryY", nativeQuery = true)
    List<String> findUsersWithTwoItemsOnSameDayAndCategories(@Param("categoryX") String categoryX,
                                                             @Param("categoryY") String categoryY);

    @Query(value="SELECT DISTINCT u.username FROM User u", nativeQuery = true)
    List<String> findAllDistinctUsernames();

    @Query("SELECT DISTINCT u FROM User u " +
            "WHERE NOT EXISTS (" +
            "   SELECT 1 FROM Item i " +
            "   JOIN i.reviews r " +
            "   WHERE i.user = u " +
            "     AND r.rating = 'excellent' " +
            "   GROUP BY i " +
            "   HAVING COUNT(r) >= 3" +
            ")")
    List<User> findUsersWhoNeverPostedExcellentItems();

    @Query("SELECT DISTINCT u " +
            "FROM User u " +
            "WHERE u.username NOT IN " +
            "(SELECT DISTINCT r.user.username " +
            " FROM Review r " +
            " WHERE r.rating = 'poor')")
    List<User> findUsersWithoutPoorReviews();

   @Query("SELECT u " +
           "FROM User u " +
           "WHERE u.username IN (" +
           "   SELECT r.user.username " +
           "   FROM Review r " +
           "   WHERE r.rating = 'poor' " +
           "   GROUP BY r.user " +
           "   HAVING COUNT(r.rating) = COUNT(*)" +
           ")")
   List<User> findUsersWithAllPoorReviews();

    @Query("SELECT DISTINCT u FROM User u " +
            "WHERE NOT EXISTS (" +
            "   SELECT 1 FROM Item i " +
            "   LEFT JOIN i.reviews r " +
            "   WHERE i.user = u " +
            "     AND (r IS NULL OR r.rating = 'poor')" +
            "     AND (SIZE(i.reviews) > 0 OR SIZE(i.reviews) = 0)" +
            ")")
    List<User> findUsersWithItemsNeverReceivedPoorReviews();

 @Query(value = "SELECT DISTINCT u1.* FROM user u1 " +
         "JOIN user u2 ON u1.username <> u2.username " +
         "JOIN item i1 ON u1.username = i1.username " +
         "JOIN item i2 ON u2.username = i2.username " +
         "JOIN review r1 ON i1.item_id = r1.item_id " +
         "JOIN review r2 ON i2.item_id = r2.item_id " +
         "WHERE u1.username = r2.username " +
         "AND u2.username = r1.username " +
         "AND r1.rating = 'excellent' " +
         "AND r2.rating = 'excellent'", nativeQuery = true)
 List<User> findUserPairsWithExcellentReviews();


}
