package com.lxc.service.impl;

import com.lxc.constants.AddResults;
import com.lxc.constants.BookStatus;
import com.lxc.entity.Book;
import com.lxc.exception.StockNotEnoughException;
import com.lxc.repository.BookRepository;
import com.lxc.service.BookService;
import com.lxc.utils.ListConvertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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

        newBook.changeStatusTo(BookStatus.AVAILABLE);
        Book temp = this.findById(newBook.getId());
        if (null != temp) {
            BeanUtils.copyProperties(newBook, temp);
            bookRepository.saveAndFlush(temp);
        }
    }

    @Override
    public void deleteById(Integer id) {

        if (null != this.findById(id)) {
            bookRepository.deleteById(id);
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
    public Page<Book> findByIsbn(String isbn) {

        try {
            Book book = bookRepository.findByIsbn(isbn);
            return bookToBookPage(book);
        } catch (EntityNotFoundException e) {
            return bookToBookPage(null);
        }
    }

    @Override
    public void setStatus(BookStatus status, Integer id) {

        Book book = this.findById(id);
        if (null != book) {
            book.changeStatusTo(status);
            bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public Book findById(Integer id) {

        try {
            return bookRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            log.error("BookId = {} does not exist!", id);
            return null;
        }
    }

    @Override
    public AddResults addBook(Book book) {

        if (bookRepository.findByIsbn(book.getIsbn()) == null) {
            book.changeStatusTo(BookStatus.AVAILABLE);
            bookRepository.saveAndFlush(book);
            return AddResults.SUCCESS;
        }
        return AddResults.FAIL;
    }

    @Override
    public void decreaseStock(Integer id, Integer decrement) throws StockNotEnoughException {

        Book book = this.findById(id);
        if (null != book) {
            try {
                book.decreaseStock(decrement);
                bookRepository.saveAndFlush(book);
            } catch (StockNotEnoughException e) {
                log.error("BookId = {} does not have enough stock!", id);
                throw new StockNotEnoughException("STOCK_IS_NOT_ENOUGH");
            }
        }
    }

    @Override
    public void increaseStock(Integer id, Integer increment) {

        Book book = this.findById(id);
        if (null != book) {
            book.increaseStock(increment);
            bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public void saveUrl(Integer id, String url) {

        Book book = this.findById(id);
        if (null != book) {
            book.setImgUrl(url);
            bookRepository.saveAndFlush(book);
        }
    }

    public Page<Book> bookToBookPage(Book book) {

        List<Book> books = book != null ? List.of(book) : List.of();
        Pageable pageable = PageRequest.of(0, 1, new Sort(Sort.Direction.ASC, "id"));
        return ListConvertor.listConvertToPage(books, pageable);
    }
}
