package com.aptech.book.service;

import com.aptech.book.entity.Book;
import com.aptech.book.exception.BookNotFoundException;
import com.aptech.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BookService {
    public static final int BOOKS_PER_PAGE = 4;

    @Autowired
    private BookRepository repo;

    public List<Book> listAll() {
        return (List<Book>) repo.findAll();
    }

    public Page<Book> listByPage(int pageNum, String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, BOOKS_PER_PAGE, sort);

        if(keyword != null) {
            return repo.findAll(keyword, pageable);
        }

        return repo.findAll(pageable);
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

    public String checkUnique(Integer id, String title) {
        boolean isCreatingNew = (id == null || id == 0);

        Book bookByTitle = repo.findByTitle(title);

        if (isCreatingNew) {
            if (bookByTitle != null) {
                return "DuplicateTitle";
            }
        } else {
            if (bookByTitle != null && bookByTitle.getId() != id) {
                return "DuplicateTitle";
            }
        }

        return "OK";
    }
}
