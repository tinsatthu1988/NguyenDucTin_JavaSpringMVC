package com.aptech.book.controller;

import com.aptech.book.FileUploadUtil;
import com.aptech.book.entity.Book;
import com.aptech.book.entity.Category;
import com.aptech.book.service.BookService;
import com.aptech.book.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/books")
    public String listAll(Model model) {
        List<Book> listBooks = bookService.listAll();

        model.addAttribute("listBooks", listBooks);

        return "books/books";
    }

    @GetMapping("/books/new")
    public String newBook(Model model) {
        List<Category> listCategories = categoryService.listAll();

        model.addAttribute("book", new Book());
        model.addAttribute("pageTitle", "Create New Book");
        model.addAttribute("listCategories", listCategories);

        return "books/book_form";
    }

    @PostMapping("/books/save")
    public String saveBook(Book book,
                               @RequestParam("fileImage") MultipartFile multipartFile,
                               RedirectAttributes ra) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            book.setImageName(fileName);

            Book savedBook = bookService.save(book);
            String uploadDir = "book-images/" + savedBook.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            bookService.save(book);
        }

        ra.addFlashAttribute("message", "The book has been saved successfully.");
        return "redirect:/books";
    }
}
