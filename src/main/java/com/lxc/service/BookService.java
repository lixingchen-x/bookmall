package com.lxc.service;

import com.lxc.constants.AddResults;
import com.lxc.entity.Book;
import org.springframework.data.domain.Page;

public interface BookService {

    Page<Book> findAllByPage(int pageNum);

    Page<Book> findPageableByCondition(String condition, String keyword, int pageNum);

    Book findByIsbn(String isbn);

    void update(Book book);

    void deleteById(Integer id);

    void setStatus(String status, Integer id);

    Book findById(Integer id);

    AddResults addBook(Book book);

    void decreaseStock(Integer id);

    void increaseStock(Integer id, Integer increment);
}
