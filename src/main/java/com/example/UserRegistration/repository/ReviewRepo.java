package com.example.UserRegistration.repository;

import com.example.UserRegistration.domain.Item;
import com.example.UserRegistration.domain.Review;
import com.example.UserRegistration.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

    long countByUser_UsernameAndCreatedAtBetween(String username, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
