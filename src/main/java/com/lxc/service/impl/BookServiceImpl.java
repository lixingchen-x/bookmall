package com.lxc.service.impl;

import com.lxc.entity.Book;
import com.lxc.repository.BookRepository;
import com.lxc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final int PAGE_SIZE=10;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Page<Book> findAllByPage(int pageNum) {

        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, sort);
        return bookRepository.findAll(pageable);
    }

    @Override
    public void update(Book book) {

        Book oldBook = bookRepository.getOne(book.getId());
        oldBook.setBookName(book.getBookName());
        oldBook.setAuthor(book.getAuthor());
        oldBook.setPublishDate(book.getPublishDate());
        oldBook.setIsbn(book.getIsbn());
        oldBook.setPrice(book.getPrice());
        oldBook.setIntro(book.getIntro());
        oldBook.setStock(book.getStock());
        oldBook.setImage(book.getImage()); //ToDo
        bookRepository.saveAndFlush(oldBook);
    }

    @Override
    public List<Book> findByBookName(String bookName) {

        return bookRepository.findByBookName(bookName);
    }

    @Override
    public List<Book> findByAuthor(String author) {

        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> findByIsbn(String isbn) {

        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public void deleteById(Integer id) {

        bookRepository.deleteById(id);
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
    public void decreaseStock(Book book) {

        Book oldBook = bookRepository.getOne(book.getId());
        oldBook.setStock(book.getStock() - 1);
        bookRepository.saveAndFlush(oldBook);
    }

    @Override
    public void increaseStock(Book book) {

        Book oldBook = bookRepository.getOne(book.getId());
        oldBook.setStock(book.getStock() + 1);
        bookRepository.saveAndFlush(oldBook);
    }
}
