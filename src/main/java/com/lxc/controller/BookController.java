package com.lxc.controller;

import com.lxc.constants.AddResults;
import com.lxc.constants.BookStatus;
import com.lxc.entity.Book;
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
    public String addBook(Book book, Model model) {

        if (AddResults.FAIL.equals(bookService.addBook(book))) {
            model.addAttribute("addBook", "新增图书失败，图书已存在！");
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
        bookService.setStatus(BookStatus.WITHDRAW, id);
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
        bookService.setStatus(BookStatus.AVAILABLE, id);
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

    private void addModelAttributes(Model model, String keyword, Integer page, String condition) {

        model.addAttribute("page", page);
        model.addAttribute("condition", condition);
        model.addAttribute("keyword", keyword);
    }
}
