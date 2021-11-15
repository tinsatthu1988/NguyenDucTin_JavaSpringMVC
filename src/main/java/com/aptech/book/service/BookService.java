package com.aptech.book.service;

import com.aptech.book.entity.Book;
import com.aptech.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository repo;

    public List<Book> listAll() {
        return (List<Book>) repo.findAll();
    }

    public Book save(Book book) {
        return repo.save(book);
    }
}
