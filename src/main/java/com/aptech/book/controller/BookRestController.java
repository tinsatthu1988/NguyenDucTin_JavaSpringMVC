package com.aptech.book.controller;

import com.aptech.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookRestController {
    @Autowired
    private BookService service;

    @PostMapping("/books/check_unique")
    public String checkUnique(@Param("id") Integer id, @Param("title") String title) {
        return service.checkUnique(id, title);
    }
}
