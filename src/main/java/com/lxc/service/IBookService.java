package com.lxc.service;

import com.lxc.entity.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBookService {

    Page<Book> findAllByPage(int pageNum);

    List<Book> findByBookName(String bookName);

    List<Book> findByAuthor(String author);

    List<Book> findByIsbn(String isbn);

    void updateBook(Book book);

    void deleteById(Integer id);

    Book getOne(Integer id);

    void saveBook(Book book);
    //ToDo
}
