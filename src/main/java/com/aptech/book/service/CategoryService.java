package com.aptech.book.service;

import com.aptech.book.entity.Category;
import com.aptech.book.exception.CategoryNotFoundException;
import com.aptech.book.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepository repo;

    public List<Category> listAll() {
        return (List<Category>) repo.findAll();
    }

    public Category save(Category category) {
        return repo.save(category);
    }

    public Category get(Integer id) throws CategoryNotFoundException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new CategoryNotFoundException("Could not find any category with ID " + id);
        }
    }

    public String checkUnique(Integer id, String name) {
        boolean isCreatingNew = (id == null || id == 0);

        Category categoryByName = repo.findByName(name);

        if (isCreatingNew) {
            if (categoryByName != null) {
                return "DuplicateName";
            }
        } else {
            if (categoryByName != null && categoryByName.getId() != id) {
                return "DuplicateName";
            }
        }

        return "OK";
    }
}
