package com.example.UserRegistration.repository;

import com.example.UserRegistration.domain.Category;
import com.example.UserRegistration.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByCategoryName(String trim);
}
