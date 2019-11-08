package com.lxc.controller;

import com.lxc.entity.Book;
import com.lxc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.lxc.utils.Convertor;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 图书展示页面
     * @param model
     * @return
     */
    @GetMapping("books")
    public String findAll(Model model, @RequestParam(defaultValue = "0") Integer page) {

        model.addAttribute("bookPage", bookService.findAllByPage(page));
        model.addAttribute("page", page);
        model.addAttribute("condition", "all");
        return "bookManagement/bookList.html";
    }

    /**
     * 图书添加的跳转
     * @return
     */
    @GetMapping("add")
    public String toAddBook() {
        return "bookManagement/addBook.html";
    }

    /**
     * 图书添加
     * @param book
     * @return
     */
    @PostMapping("add")
    public String addBook(Book book, HttpServletRequest request) {

        if ("fail".equals(bookService.addBook(book))) {
            request.setAttribute("addBook", "新增图书失败，图书已存在！");
            return "bookManagement/addBook.html";
        }
        return "redirect:/book/books";
    }

    /**
     * 跳转到修改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("update/{bookId}")
    public String toUpdateBook(@PathVariable("bookId") Integer id, Model model) {

        model.addAttribute("book", bookService.findById(id));
        return "bookManagement/editBook.html";
    }

    /**
     * 编辑图书信息
     * @param book
     * @return
     */
    @PutMapping("update")
    public String updateBook(Book book) {

        book.setStatus("AVAILABLE");
        bookService.update(book);
        return "redirect:/book/books";
    }

    /**
     * 下架书籍
     * @param id
     * @param page
     * @param model
     * @return
     */
    @RequestMapping("withdraw")
    public String withdrawBook(@RequestParam(value = "bookId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        bookService.setStatus("WITHDRAW", id);
        return "redirect:/book/books";
    }

    /**
     * 上架书籍
     * @param id
     * @param page
     * @param model
     * @return
     */
    @RequestMapping("onSale")
    public String onSaleBook(@RequestParam(value = "bookId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        bookService.setStatus("AVAILABLE", id);
        return "redirect:/book/books";
    }

    /**
     * 从数据库中永久删除
     * @param id
     * @return
     */
    @RequestMapping("delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") Integer id) {

        bookService.deleteById(id);
        return "redirect:/book/books";
    }

    @RequestMapping("findByName")
    public String findBookByName(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {

        request.setAttribute("bookPage", bookService.findPageableByCondition("name", keyword, page));
        setRequestAttributes(request, keyword, page, "name");
        return "bookManagement/bookList.html";
    }

    @RequestMapping("findByAuthor")
    public String findBookByAuthor(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {

        request.setAttribute("bookPage", bookService.findPageableByCondition("author", keyword, page));
        setRequestAttributes(request, keyword, page, "author");
        return "bookManagement/bookList.html";
    }

    @RequestMapping("findByIsbn")
    public String findBookByIsbn(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {

        Page<Book> bookPage = bookToBookPage(bookService.findByIsbn(keyword));
        request.setAttribute("bookPage", bookPage);
        setRequestAttributes(request, keyword, page, "isbn");
        return "bookManagement/bookList.html";
    }

    private void setRequestAttributes(HttpServletRequest request, String keyword, Integer page, String condition) {

        request.setAttribute("page", page);
        request.setAttribute("condition", condition);
        request.setAttribute("keyword", keyword);
    }

    private Page<Book> bookToBookPage(Book book) {

        List<Book> books = new ArrayList<>();
        books.add(book);
        Pageable pageable = PageRequest.of(0, 1, new Sort(Sort.Direction.ASC, "id"));
        return Convertor.listConvertToPage(books, pageable);
    }
}
