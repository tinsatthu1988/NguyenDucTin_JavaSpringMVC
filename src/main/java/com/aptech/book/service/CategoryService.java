package com.aptech.book.service;

import com.aptech.book.entity.Category;
import com.aptech.book.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repo;

    public List<Category> listAll() {
        return (List<Category>) repo.findAll();
    }
}
