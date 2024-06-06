package com.example.UserRegistration.repository;


import com.example.UserRegistration.domain.Favorite;
import com.example.UserRegistration.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepo extends JpaRepository<Favorite, Long> {

    @Query("SELECT f.favoriteUser FROM Favorite f WHERE f.user.username = :username1 AND f.favoriteUser IN " +
            "(SELECT f2.favoriteUser FROM Favorite f2 WHERE f2.user.username = :username2)")
    List<User> findCommonFavorites(@Param("username1") String username1, @Param("username2") String username2);
}
