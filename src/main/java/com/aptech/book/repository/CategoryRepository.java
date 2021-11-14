package com.aptech.book.repository;

import com.aptech.book.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Category findByName(String name);
}
