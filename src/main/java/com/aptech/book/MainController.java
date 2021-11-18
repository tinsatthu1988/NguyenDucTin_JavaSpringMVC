package com.aptech.book;

import com.aptech.book.entity.Book;
import com.aptech.book.entity.Category;
import com.aptech.book.service.BookService;
import com.aptech.book.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
class MainController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/home")
    public String listFirstPageHome(Model model) {

        return listByPageHome(1, model, null);
    }

    @GetMapping("/home/page/{pageNum}")
    public String listByPageHome(
            @PathVariable(name="pageNum") int pageNum, Model model,
            @Param("keyword") String keyword
    ) {
        Page<Book> page = bookService.listByPage(pageNum, keyword);
        List<Book> listBooks = page.getContent();
        List<Category> listCategories = categoryService.listAll();

        long startCount = (pageNum - 1) * bookService.BOOKS_PER_PAGE + 1;
        long endCount = startCount + bookService.BOOKS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }


        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listBooks", listBooks);
        model.addAttribute("keyword", keyword);
        model.addAttribute("listCategories", listCategories);

        return "home";
    }

    @GetMapping("/index")
    public String viewIndexPage() {
        return "index";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        return "redirect:/index";
    }
}
