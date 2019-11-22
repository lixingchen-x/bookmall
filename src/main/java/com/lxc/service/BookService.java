package com.lxc.service;

import com.lxc.constants.ResultEnum;
import com.lxc.constants.BookStatusEnum;
import com.lxc.entity.Book;
import com.lxc.exception.StockNotEnoughException;
import org.springframework.data.domain.Page;

public interface BookService {

    /**
     * retrieve pageable books by page number
     *
     * @param pageNum
     * @return
     */
    Page<Book> findAllByPage(int pageNum);

    /**
     * retrieve pageable books by different conditions
     *
     * @param condition
     * @param keyword
     * @param pageNum
     * @return
     */
    Page<Book> findPageableByCondition(String condition, String keyword, int pageNum);

    /**
     * retrieve a single book as Page type by isbn
     *
     * @param isbn
     * @return
     */
    Page<Book> findByIsbn(String isbn);

    /**
     * update a book with new information
     *
     * @param book
     */
    void update(Book book);

    /**
     * delete a book by bookId
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * set a book's status by bookId
     *
     * @param status
     * @param id
     */
    void setStatus(BookStatusEnum status, Integer id);

    /**
     * retrieve a book by bookId
     *
     * @param id
     * @return
     */
    Book findById(Integer id);

    /**
     * add a new book into database
     * return success or fail
     *
     * @param book
     * @return
     */
    ResultEnum addBook(Book book);

    /**
     * decrease a book's stock by some decrement
     *
     * @param id
     * @param decrement
     * @throws StockNotEnoughException
     */
    void decreaseStock(Integer id, Integer decrement) throws StockNotEnoughException;

    /**
     * increase a book's stock by some increment
     *
     * @param id
     * @param increment
     */
    void increaseStock(Integer id, Integer increment);

    /**
     * persist a book's image by way of saving url
     *
     * @param id
     * @param url
     */
    void saveUrl(Integer id, String url);
}
