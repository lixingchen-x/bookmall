package com.lxc.controller;

import com.lxc.entity.Book;
import com.lxc.repository.BookRepository;
import com.lxc.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 书的表现层
 */
@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookServiceImpl bookService;

    /**
     * 图书展示页面
     * @param model
     * @return
     */
    @GetMapping("books")
    public String findAll(Model model, @RequestParam(value = "page", required = false) Integer page){

        if(page == null){
            page = 0;
            model.addAttribute("page", 0);
        }
        Page<Book> bookPages = bookService.findAllByPage(page);
        List<Book> bookList=bookPages.getContent();
        model.addAttribute("bookList", bookList);
        model.addAttribute("totalPages", bookPages.getTotalPages());
        model.addAttribute("page", page);
        return "bookManagement/bookList.html";
    }

    /**
     * 图书添加的跳转
     * @return
     */
    @GetMapping("add")
    public String toAddBook(){
        return "bookManagement/addBook.html";
    }

    /**
     * 图书添加
     * @param book
     * @return
     */
    @PostMapping("add")
    public String addBook(Book book){
        bookRepository.saveAndFlush(book);
        return "redirect:/book/books";
    }

    /**
     * 跳转到修改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("update/{bookId}")
    public String toUpdateBook(@PathVariable("bookId") Integer id, Model model){
        Book book = bookRepository.getOne(id);
        model.addAttribute("book",book);
        return "bookManagement/editBook.html";
    }

    /**
     * 编辑图书信息
     * @param book
     * @return
     */
    @PutMapping("update")
    public String updateBook(Book book){
        bookService.updateBook(book);
        return "redirect:/book/books";
    }

    /**
     * 删除图书
     * @param id
     * @return
     */
    @RequestMapping("delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") Integer id){
        bookRepository.deleteById(id);
        return "redirect:/book/books";
    }

    //查询 ToDo
}
