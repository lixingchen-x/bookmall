package com.lxc.controller;

import com.lxc.constants.AddResultEnum;
import com.lxc.constants.BookStatusEnum;
import com.lxc.entity.Book;
import com.lxc.service.BookService;
import com.lxc.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private FileService fileService;

    @GetMapping("books")
    public String findAll(Model model, @RequestParam(defaultValue = "0") Integer page) {

        model.addAttribute("bookPage", bookService.findAllByPage(page));
        model.addAttribute("page", page);
        model.addAttribute("condition", "all");
        return "bookManagement/bookList.html";
    }

    @GetMapping("add")
    public String toAddBook() {
        return "bookManagement/addBook.html";
    }

    @PostMapping("add")
    public String addBook(Book book, Model model) {

        if (AddResultEnum.FAIL.equals(bookService.addBook(book))) {
            model.addAttribute("addBook", "新增图书失败，图书已存在！");
            return "bookManagement/addBook.html";
        }
        return "redirect:/book/books";
    }

    @GetMapping("update/{bookId}")
    public String toUpdateBook(@PathVariable("bookId") Integer id, Model model) {

        model.addAttribute("book", bookService.findById(id));
        return "bookManagement/editBook.html";
    }

    @PutMapping("update")
    public String updateBook(Book book) {

        bookService.update(book);
        return "redirect:/book/books";
    }

    @RequestMapping("withdraw")
    public String withdrawBook(@RequestParam(value = "bookId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        bookService.setStatus(BookStatusEnum.WITHDRAW, id);
        return "redirect:/book/books";
    }

    @RequestMapping("onSale")
    public String onSaleBook(@RequestParam(value = "bookId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        bookService.setStatus(BookStatusEnum.AVAILABLE, id);
        return "redirect:/book/books";
    }

    @RequestMapping("delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") Integer id) {

        bookService.deleteById(id);
        return "redirect:/book/books";
    }

    @RequestMapping("findByName")
    public String findBookByName(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("bookPage", bookService.findPageableByCondition("name", keyword, page));
        addModelAttributes(model, keyword, page, "name");
        return "bookManagement/bookList.html";
    }

    @RequestMapping("findByAuthor")
    public String findBookByAuthor(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("bookPage", bookService.findPageableByCondition("author", keyword, page));
        addModelAttributes(model, keyword, page, "author");
        return "bookManagement/bookList.html";
    }

    @RequestMapping("findByIsbn")
    public String findBookByIsbn(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("bookPage", bookService.findByIsbn(keyword));
        addModelAttributes(model, keyword, page, "isbn");
        return "bookManagement/bookList.html";
    }

    @GetMapping("upload")
    public String toAddBookImage() {
        return "bookManagement/upload.html";
    }

    @RequestMapping("upload/{bookId}")
    public String imageUpload(@PathVariable("bookId") Integer id, @RequestParam("file") MultipartFile file, Model model) {

        String url = fileService.upload(file);
        bookService.saveUrl(id, url);
        model.addAttribute("url", url);
        return "bookManagement/upload.html";
    }

    private void addModelAttributes(Model model, String keyword, Integer page, String condition) {

        model.addAttribute("page", page);
        model.addAttribute("condition", condition);
        model.addAttribute("keyword", keyword);
    }
}
