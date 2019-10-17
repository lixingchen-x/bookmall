package com.lxc.service;

import com.lxc.entity.Book;

import java.util.Iterator;

public interface IBookService {

    Iterator<Book> findAll(int pageNum, int pageSize);
    //ToDo
}
