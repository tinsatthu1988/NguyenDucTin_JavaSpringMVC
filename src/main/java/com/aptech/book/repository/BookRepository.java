package com.aptech.book.repository;

import com.aptech.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer> {
    public Book findByTitle(String title);

    @Query("SELECT b FROM Book b WHERE CONCAT(b.id , ' ', b.title, ' ', b.author, ' ', b.description, ' ', b.category.name) LIKE %?1%")
    public Page<Book> findAll(String keyword, Pageable pageable);
}
