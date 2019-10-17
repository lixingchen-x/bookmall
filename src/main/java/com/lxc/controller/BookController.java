package com.lxc.controller;

import com.lxc.entity.Book;
import com.lxc.repository.BookRepository;
import com.lxc.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 书的表现层
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookServiceImpl bookService;

    @GetMapping("/findByName/{bookName}")
    public List<Book> findBookById(@PathVariable("bookName") String bookName){

        List<Book> books = bookRepository.findByBookName(bookName);
        return books;
    }

    @PostMapping("/addBook")
    public String addBook(HttpServletRequest request, @ModelAttribute Book book){

        book.setPublishDate(new Date());
        bookRepository.saveAndFlush(book);
        return "success";
    }

    @GetMapping("/findAll/{pageNum}/{pageSize}")
    public List<Book> findAll(@PathVariable("pageNum") int pageNum, @PathVariable int pageSize){
        Iterator<Book> bookIterator = bookService.findAll(pageNum,pageSize);
        List<Book> books = new ArrayList<>();
        while (bookIterator.hasNext()){
            books.add(bookIterator.next());
        }
        return books;
    }
}
