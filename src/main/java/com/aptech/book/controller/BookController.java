package com.aptech.book.controller;

import com.aptech.book.FileUploadUtil;
import com.aptech.book.entity.Book;
import com.aptech.book.entity.Category;
import com.aptech.book.exception.BookNotFoundException;
import com.aptech.book.exception.CategoryNotFoundException;
import com.aptech.book.service.BookService;
import com.aptech.book.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String listFirstPage(Model model) {
        return listByPage(1, model, "title", "asc", null);
    }

    @GetMapping("/books/page/{pageNum}")
    public String listByPage(
            @PathVariable(name="pageNum") int pageNum, Model model,
            @Param("sortField") String sortField, @Param("sortDir") String sortDir,
            @Param("keyword") String keyword
    ) {

        Page<Book> page = bookService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Book> listBooks = page.getContent();

        long startCount = (pageNum - 1) * bookService.BOOKS_PER_PAGE + 1;
        long endCount = startCount + bookService.BOOKS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listBooks", listBooks);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);

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

    @GetMapping("/books/edit/{id}")
    public String editBook(@PathVariable(name = "id") Integer id, Model model,
                               RedirectAttributes ra) {
        try {
            Book book = bookService.get(id);
            List<Category> listCategories = categoryService.listAll();

            model.addAttribute("book", book);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Edit Book (ID: " + id + ")");

            return "books/book_form";
        } catch (BookNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return "redirect:/books";
        }
    }
}
