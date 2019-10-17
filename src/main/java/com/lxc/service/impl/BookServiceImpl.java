package com.lxc.service.impl;

import com.lxc.entity.Book;
import com.lxc.repository.BookRepository;
import com.lxc.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Iterator;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Iterator<Book> findAll(int pageNum, int pageSize) {

        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Page<Book> books = bookRepository.findAll(pageable);
        System.out.println("page为： "+books);
        Iterator<Book> bookIterator = books.iterator();
        return bookIterator;
    }
    //ToDo
}
