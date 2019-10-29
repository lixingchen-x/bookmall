package com.lxc.service;

import com.lxc.entity.Book;
import com.lxc.repository.BookRepository;
import com.lxc.service.impl.BookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    private final int PAGE_SIZE = 10;

    private final int BOOK_ID = 1;



    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void findAllByPage_happyPath() {


    }

    @Test
    public void update_happyPath() {
        Book oldBook = new Book();
        Book newBook = new Book();
        when(bookRepository.getOne(oldBook.getId())).thenReturn(newBook);
        bookService.update(oldBook);
        verify(bookRepository, times(1)).saveAndFlush(newBook);
    }

    @Test
    public void deleteById_happyPath() {

        bookService.deleteById(BOOK_ID);
        verify(bookRepository, times(1)).deleteById(BOOK_ID);
    }

    @Test
    public void findByCondition_happyPath() {


    }

    @Test
    public void setStatus_happyPath() {

        Book actual = createBook();
        when(bookRepository.getOne(BOOK_ID)).thenReturn(actual);
        bookService.setStatus("AVAILABLE", BOOK_ID);
        verify(bookRepository, times(1)).getOne(BOOK_ID);
        verify(bookRepository, times(1)).saveAndFlush(actual);
        assertEquals("AVAILABLE", actual.getStatus());
    }

    @Test
    public void findById_happyPath() {

        Book expected = new Book();
        when(bookRepository.getOne(BOOK_ID)).thenReturn(expected);
        Book actual = bookService.findById(BOOK_ID);
        assertSame(expected, actual);
    }

    @Test
    public void save_happyPath() {

        Book book = createBook();
        bookService.save(book);
        verify(bookRepository, times(1)).saveAndFlush(book);
    }

    @Test
    public void decreaseStock_happyPath() {


    }

    @Test
    public void increaseStock_happyPath() {


    }

    private Book createBook() {

        Book book = new Book("abc");
        book.setId(BOOK_ID);
        return book;
    }
}
