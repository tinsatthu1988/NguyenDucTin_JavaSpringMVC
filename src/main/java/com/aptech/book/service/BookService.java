package com.aptech.book.service;

import com.aptech.book.entity.Book;
import com.aptech.book.entity.Category;
import com.aptech.book.exception.BookNotFoundException;
import com.aptech.book.exception.CategoryNotFoundException;
import com.aptech.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Book get(Integer id) throws BookNotFoundException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new BookNotFoundException("Could not find any book with ID " + id);
        }
    }
}
