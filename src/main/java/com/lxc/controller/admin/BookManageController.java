package com.lxc.controller.admin;

import com.lxc.constants.BookStatus;
import com.lxc.constants.ResultEnum;
import com.lxc.entity.Book;
import com.lxc.service.BookService;
import com.lxc.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class BookManageController {

    @Autowired
    private BookService bookService;

    @Autowired
    private FileService fileService;

    @GetMapping("book/add")
    public String toAddBook() {
        return "bookManagement/addBook.html";
    }

    @PostMapping("book/add")
    public String addBook(Book book, Model model) {

        if (ResultEnum.FAIL.equals(bookService.addBook(book))) {
            model.addAttribute("addBook", "新增图书失败，图书已存在！");
            return "bookManagement/addBook.html";
        }
        return "redirect:/book/books";
    }

    @GetMapping("book/update/{bookId}")
    public String toUpdateBook(@PathVariable("bookId") Integer id, Model model) {

        model.addAttribute("book", bookService.findById(id));
        return "bookManagement/editBook.html";
    }

    @PutMapping("book/update")
    public String updateBook(Book book) {

        bookService.update(book);
        return "redirect:/book/books";
    }

    @RequestMapping("book/withdraw")
    public String withdrawBook(@RequestParam(value = "bookId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        bookService.setStatus(BookStatus.WITHDRAW, id);
        return "redirect:/book/books";
    }

    @RequestMapping("book/onSale")
    public String onSaleBook(@RequestParam(value = "bookId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        bookService.setStatus(BookStatus.AVAILABLE, id);
        return "redirect:/book/books";
    }

    @RequestMapping("book/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") Integer id) {

        bookService.deleteById(id);
        return "redirect:/book/books";
    }

    @GetMapping("book/upload")
    public String toAddBookImage() {
        return "bookManagement/upload.html";
    }

    @RequestMapping("book/upload/{bookId}")
    public String imageUpload(@PathVariable("bookId") Integer id, @RequestParam MultipartFile file, Model model) throws IOException {

        String url = fileService.upload(file);
        bookService.saveUrl(id, url);
        model.addAttribute("url", url);
        return "bookManagement/upload.html";
    }
}
