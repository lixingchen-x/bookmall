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

@Service
public class BookServiceImpl implements IBookService {

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
    public void updateBook(Book book) {

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

    //ToDo
}
