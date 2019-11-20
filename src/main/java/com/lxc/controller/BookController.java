package com.lxc.controller;

import com.lxc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("books")
    public String findAll(Model model, @RequestParam(defaultValue = "0") Integer page) {

        model.addAttribute("bookPage", bookService.findAllByPage(page));
        model.addAttribute("page", page);
        model.addAttribute("condition", "all");
        return "bookManagement/bookList.html";
    }

    @RequestMapping("findByName")
    public String findBookByName(@RequestParam(required = false) String keyword,
                                 @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("bookPage", bookService.findPageableByCondition("name", keyword, page));
        addModelAttributes(model, keyword, page, "name");
        return "bookManagement/bookList.html";
    }

    @RequestMapping("findByAuthor")
    public String findBookByAuthor(@RequestParam(required = false) String keyword,
                                 @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("bookPage", bookService.findPageableByCondition("author", keyword, page));
        addModelAttributes(model, keyword, page, "author");
        return "bookManagement/bookList.html";
    }

    @RequestMapping("findByIsbn")
    public String findBookByIsbn(@RequestParam(required = false) String keyword,
                                 @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("bookPage", bookService.findByIsbn(keyword));
        addModelAttributes(model, keyword, page, "isbn");
        return "bookManagement/bookList.html";
    }

    private void addModelAttributes(Model model, String keyword, Integer page, String condition) {

        model.addAttribute("page", page);
        model.addAttribute("condition", condition);
        model.addAttribute("keyword", keyword);
    }
}
