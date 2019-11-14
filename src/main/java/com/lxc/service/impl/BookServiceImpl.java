package com.lxc.service.impl;

import com.lxc.constants.AddResults;
import com.lxc.constants.BookStatus;
import com.lxc.entity.Book;
import com.lxc.exception.StockNotEnoughException;
import com.lxc.repository.BookRepository;
import com.lxc.service.BookService;
import com.lxc.utils.ListConvertor;
import com.lxc.utils.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final int PAGE_SIZE = 10;

    @Autowired
    private BookRepository bookRepository;

    @Value("${file.upload.path}")
    private String filePath;

    @Override
    public Page<Book> findAllByPage(int pageNum) {

        Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));
        return bookRepository.findAll(pageable);
    }

    @Override
    public void update(Book newBook) {

        try {
            newBook.changeStatusTo(BookStatus.AVAILABLE);
            Book temp = bookRepository.getOne(newBook.getId());
            BeanUtils.copyProperties(newBook, temp);
            bookRepository.saveAndFlush(temp);
        } catch (EntityNotFoundException e) {
            log.error("BookId = {} does not exist!", newBook.getId());
        }
    }

    @Override
    public void deleteById(Integer id) {

        try {
            bookRepository.getOne(id);
            bookRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            log.error("BookId = {} does not exist!", id);
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

        try {
            Book book = bookRepository.getOne(id);
            book.changeStatusTo(status);
            bookRepository.saveAndFlush(book);
        } catch (EntityNotFoundException e) {
            log.error("BookId = {} does not exist!", id);
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
    public void decreaseStock(Integer id, Integer decrement) {

        Book book = bookRepository.getOne(id);
        try {
            book.decreaseStock(decrement);
            bookRepository.saveAndFlush(book);
        } catch (StockNotEnoughException e) {
            log.error("BookId = {} does not have enough stock!", id);
        }
    }

    @Override
    public void increaseStock(Integer id, Integer increment) {

        Book book = bookRepository.getOne(id);
        book.increaseStock(increment);
        bookRepository.saveAndFlush(book);
    }

    @Override
    public String uploadImg(MultipartFile file) {

        String filename = file.getOriginalFilename();
        File destination = new File(filePath, filename);
        String uniqueId = UUIDGenerator.getUUID(filename);
        try {
            file.transferTo(destination);
        } catch (IOException e) {
            log.error("{} failed writing into directory.", filename);
        }
        return filePath+uniqueId;
    }

    @Override
    public void saveUrl(Integer id, String url) {

        Book book = bookRepository.getOne(id);
        book.setImgUrl(url);
        bookRepository.saveAndFlush(book);
    }

    public Page<Book> bookToBookPage(Book book) {

        if (book == null) {
            book = Book.builder().id(1).build();
        }
        Pageable pageable = PageRequest.of(0, 1, new Sort(Sort.Direction.ASC, "id"));
        return ListConvertor.listConvertToPage(List.of(book), pageable);
    }
}
