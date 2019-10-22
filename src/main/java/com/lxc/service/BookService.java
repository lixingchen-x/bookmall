package com.lxc.service;

import com.lxc.entity.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    Page<Book> findAllByPage(int pageNum);

    List<Book> findByBookName(String bookName);

    List<Book> findByAuthor(String author);

    List<Book> findByIsbn(String isbn);

    void update(Book book);

    void deleteById(Integer id);

    Book findById(Integer id);

    void save(Book book);

    void decreaseStock(Book book);

    void increaseStock(Book book);
}
