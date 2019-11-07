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

import javax.persistence.EntityNotFoundException;

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
    public void update(Book newBook) {

        try {
            Book temp = bookRepository.getOne(newBook.getId());
            BeanUtils.copyProperties(newBook, temp);
            bookRepository.saveAndFlush(temp);
        }catch (EntityNotFoundException e) {
            System.out.println("BookID does not exist!");
        }
    }

    @Override
    public void deleteById(Integer id) {

        try {
            bookRepository.getOne(id);
            bookRepository.deleteById(id);
        }catch (EntityNotFoundException e) {
            System.out.println("BookID does not exist!");
        }
    }

    @Override
    public Page<Book> findByCondition(String condition, String keyword, int pageNum) {

        Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));
        if ("name".equals(condition)) {
            return bookRepository.findByBookName(keyword, pageable);
        }else if ("author".equals(condition)) {
            return bookRepository.findByAuthor(keyword, pageable);
        }
        return bookRepository.findByIsbn(keyword, pageable);
    }

    @Override
    public void setStatus(String status, Integer id) {

        try {
            Book book = bookRepository.getOne(id);
            book.setStatus(status);
            bookRepository.saveAndFlush(book);
        }catch (EntityNotFoundException e) {
            System.out.println("BookID does not exist!");
        }
    }

    @Override
    public Book findById(Integer id) {

        try {
            return bookRepository.getOne(id);
        }catch (EntityNotFoundException e) {
            System.out.println("BookID does not exist!");
            return null;
        }
    }

    @Override
    public void save(Book book) {

        bookRepository.saveAndFlush(book);
    }

    @Override
    public void decreaseStock(Integer id) {

        try {
            Book book = bookRepository.getOne(id);
            book.decreaseStock();
            bookRepository.saveAndFlush(book);
        }catch (EntityNotFoundException e) {
            System.out.println("BookID does not exist!");
        }
    }

    @Override
    public void increaseStock(Integer id) {

        try {
            Book book = bookRepository.getOne(id);
            book.increaseStock();
            bookRepository.saveAndFlush(book);
        }catch (EntityNotFoundException e) {
            System.out.println("BookID does not exist!");
        }
    }
}
