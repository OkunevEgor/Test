package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/references")
public class BookController {


    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/addNewBook")
    public String addUser(Model model) {
        try {
            model.addAttribute("predefinedRoles", bookService.getAllBook());
            model.addAttribute("book", new Book());
            return "references/book/modal/addBook";
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "references/book/modal/addBook";
        }
    }

    @RequestMapping(value = { "/books"}, method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN')")

    public String books(Model model) {
        List<Book> list = bookService.getAllBook();
        model.addAttribute("books",list);
        return "references/book/book";
    }
}
