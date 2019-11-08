package com.lxc.service.impl;

import com.lxc.entity.Book;
import com.lxc.repository.BookRepository;
import com.lxc.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
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
            log.error("BookId = " + newBook.getId() + " does not exist!");
        }
    }

    @Override
    public void deleteById(Integer id) {

        try {
            bookRepository.getOne(id);
            bookRepository.deleteById(id);
        }catch (EntityNotFoundException e) {
            log.error("BookId = " + id + " does not exist!");
        }
    }

    @Override
    public Page<Book> findPageableByCondition(String condition, String keyword, int pageNum) {

        Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));
        if ("name".equals(condition)) {
            return bookRepository.findByBookName(keyword, pageable);
        }
        return bookRepository.findByAuthor(keyword, pageable);
    }

    @Override
    public Book findByIsbn(String isbn) {

        try {
            return bookRepository.findByIsbn(isbn);
        }catch (EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public void setStatus(String status, Integer id) {

        try {
            Book book = bookRepository.getOne(id);
            book.setStatus(status);
            bookRepository.saveAndFlush(book);
        }catch (EntityNotFoundException e) {
            log.error("BookId = " + id + " does not exist!");
        }
    }

    @Override
    public Book findById(Integer id) {

        try {
            return bookRepository.getOne(id);
        }catch (EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public String addBook(Book book) {

        if (bookRepository.findByIsbn(book.getIsbn()) == null) {
            book.setStatus("AVAILABLE");
            bookRepository.saveAndFlush(book);
            return "success";
        }
        return "fail";
    }

    @Override
    public void decreaseStock(Integer id) {

        try {
            Book book = bookRepository.getOne(id);
            book.decreaseStock();
            bookRepository.saveAndFlush(book);
        }catch (EntityNotFoundException e) {
            log.error("BookId = " + id + " does not exist!");
        }
    }

    @Override
    public void increaseStock(Integer id, Integer increment) {

        try {
            Book book = bookRepository.getOne(id);
            book.increaseStock(increment);
            bookRepository.saveAndFlush(book);
        }catch (EntityNotFoundException e) {
            log.error("BookId = " + id + " does not exist!");
        }
    }
}
