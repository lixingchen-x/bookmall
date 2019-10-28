package com.lxc.service.impl;

import com.lxc.entity.Book;
import com.lxc.repository.BookRepository;
import com.lxc.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final int PAGE_SIZE = 10;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Page<Book> findAllByPage(int pageNum) {

        Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));
        return bookRepository.findAll(pageable);
    }

    @Override
    public void update(Book book) {

        Book newBook = bookRepository.getOne(book.getId());
        BeanUtils.copyProperties(book, newBook);
        bookRepository.saveAndFlush(newBook);
    }

    @Override
    public void deleteById(Integer id) {

        bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> findByCondition(String condition, String keyword, int pageNum) {

        Page<Book> result = null;
        Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));
        switch (condition) {
            case "name" :
                result = bookRepository.findByBookName(keyword, pageable);
                break;
            case "author" :
                result = bookRepository.findByAuthor(keyword, pageable);
                break;
            case "isbn" :
                result = bookRepository.findByIsbn(keyword, pageable);
                break;
        }
        return result;
    }

    @Override
    public void setStatus(String status, Integer id) {

        Book book = bookRepository.getOne(id);
        book.setStatus(status);
        bookRepository.saveAndFlush(book);
    }

    @Override
    public Book findById(Integer id) {

        return bookRepository.getOne(id);
    }

    @Override
    public void save(Book book) {

        bookRepository.saveAndFlush(book);
    }

    @Override
    public void decreaseStock(Integer id) {

        Book book = bookRepository.getOne(id);
        book.decreaseStock();
        bookRepository.saveAndFlush(book);
    }

    @Override
    public void increaseStock(Integer id) {

        Book book = bookRepository.getOne(id);
        book.increaseStock();
        bookRepository.saveAndFlush(book);
    }
}
