package com.lxc.service;

import com.lxc.constants.ResultEnum;
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

    /**
     * add a new book into database
     * return success or fail
     */
    ResultEnum addBook(Book book);

    /**
     * decrease a book's stock by some decrement
     * @throws StockNotEnoughException
     */
    void decreaseStock(Integer id, Integer decrement) throws StockNotEnoughException;

    /**
     * increase a book's stock by some increment
     */
    void increaseStock(Integer id, Integer increment);

    /**
     * persist a book's image by way of saving url
     */
    void saveUrl(Integer id, String url);
}
