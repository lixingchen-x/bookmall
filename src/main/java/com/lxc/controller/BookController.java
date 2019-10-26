package com.lxc.controller;

import com.lxc.entity.Book;
import com.lxc.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    /**
     * 图书展示页面
     * @param model
     * @return
     */
    @GetMapping("books")
    public String findAll(Model model, @RequestParam(defaultValue = "0") Integer page) {

        model.addAttribute("bookPage", bookService.findAllByPage(page));
        model.addAttribute("page", page);
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
    public String addBook(Book book) {

        book.setBookStatus("AVAILABLE");
        bookService.save(book);
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

        book.setBookStatus("AVAILABLE");
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
        bookService.setBookStatus("WITHDRAW", id);
        return "forward:/book/find";
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
        bookService.setBookStatus("AVAILABLE", id);
        return "forward:/book/find";
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

    /**
     * 根据条件分页查询
     * @param key
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping("find")
    public String findBookByContition(@RequestParam(value = "key", required = false) String key, @RequestParam(value = "keyword", required = false) String keyword
            , Model model, @RequestParam(defaultValue = "0") Integer page, HttpSession session) {

        if(key == null && keyword == null) {
            if(session.getAttribute("key") == null && session.getAttribute("keyword") == null) {
                return "forward:/book/books";
            }
            key = session.getAttribute("key").toString();
            keyword = session.getAttribute("keyword").toString();
        }
        if("all".equals(key)) {
            return "forward:/book/books";
        }
        model.addAttribute("bookPage", bookService.findByCondition(key, keyword, page));
        model.addAttribute("page", page);
        session.setAttribute("key", key);
        session.setAttribute("keyword", keyword);
        return "bookManagement/bookList.html";
    }
}
