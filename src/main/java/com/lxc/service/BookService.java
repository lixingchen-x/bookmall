package com.lxc.service;

import com.lxc.constants.AddResult;
import com.lxc.constants.BookStatus;
import com.lxc.entity.Book;
import com.lxc.exception.StockNotEnoughException;
import org.springframework.data.domain.Page;

public interface BookService {

    Page<Book> findAllByPage(int pageNum);

    Page<Book> findPageableByCondition(String condition, String keyword, int pageNum);

    Page<Book> findByIsbn(String isbn);

    void update(Book book);

    void deleteById(Integer id);

    void setStatus(BookStatus status, Integer id);

    Book findById(Integer id);

    AddResult addBook(Book book);

    void decreaseStock(Integer id, Integer decrement) throws StockNotEnoughException;

    void increaseStock(Integer id, Integer increment);

    void saveUrl(Integer id, String url);
}
