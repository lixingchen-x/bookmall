package com.lxc.service;

import com.lxc.entity.Book;
import org.springframework.data.domain.Page;

public interface IBookService {

    Page<Book> findAllByPage(int pageNum);

    void updateBook(Book book);
    //ToDo
}
