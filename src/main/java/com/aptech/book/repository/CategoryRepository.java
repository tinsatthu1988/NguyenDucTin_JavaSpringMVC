package com.aptech.book.repository;

import com.aptech.book.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Category findByName(String name);

    public Long countById(Integer id);

    @Query("SELECT c FROM Category c WHERE CONCAT(c.id , ' ', c.name) LIKE %?1%")
    public Page<Category> findAll(String keyword, Pageable pageable);
}
