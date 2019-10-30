package com.lxc.service;

import com.lxc.entity.Book;
import com.lxc.repository.BookRepository;
import com.lxc.service.impl.BookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    private final int PAGE_SIZE = 10;

    private final int PAGE_NUM = 0;

    private Pageable pageable = PageRequest.of(PAGE_NUM, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void findAllByPage_happyPath() {

        bookService.findAllByPage(PAGE_NUM);
        verify(bookRepository).findAll(pageable);
    }

    @Test
    public void update_happyPath() {
        Book oldBook = new Book();
        Book newBook = new Book();
        when(bookRepository.getOne(oldBook.getId())).thenReturn(newBook);
        bookService.update(oldBook);
        verify(bookRepository).saveAndFlush(newBook);
    }

    @Test
    public void deleteById_happyPath() {

        bookService.deleteById(1);
        verify(bookRepository).deleteById(1);
    }

    @Test
    public void findByName_happyPath() {

        bookService.findByCondition("name", "abc", PAGE_NUM);
        verify(bookRepository).findByBookName("abc", pageable);
    }

    @Test
    public void findByName_shouldBeNull_ifBookNameDoseNotExist() {

        when(bookRepository.findByBookName("abc", pageable)).thenReturn(null);
        assertNull(bookService.findByCondition("name", "abc", PAGE_NUM));
    }

    @Test
    public void findByAuthor_happyPath() {

        bookService.findByCondition("author", "abc", PAGE_NUM);
        verify(bookRepository).findByAuthor("abc", pageable);
    }

    @Test
    public void findByAuthor_shouldBeNull_ifAuthorDoseNotExist() {

        when(bookRepository.findByAuthor("abc", pageable)).thenReturn(null);
        assertNull(bookService.findByCondition("author", "abc", PAGE_NUM));
    }

    @Test
    public void findByIsbn_happyPath() {

        bookService.findByCondition("isbn", "123", PAGE_NUM);
        verify(bookRepository).findByIsbn("123", pageable);
    }

    @Test
    public void findByIsbn_shouldBeNull_ifIsbnDoseNotExist() {

        when(bookRepository.findByIsbn("123", pageable)).thenReturn(null);
        assertNull(bookService.findByCondition("isbn", "123", PAGE_NUM));
    }

    @Test
    public void setStatus_happyPath() {

        Book actual = new Book();
        when(bookRepository.getOne(1)).thenReturn(actual);
        bookService.setStatus("AVAILABLE", 1);
        verify(bookRepository).saveAndFlush(actual);
        assertEquals("AVAILABLE", actual.getStatus());
    }

    @Test
    public void findById_happyPath() {

        Book expected = new Book();
        when(bookRepository.getOne(1)).thenReturn(expected);
        Book actual = bookService.findById(1);
        assertSame(expected, actual);
    }

    @Test
    public void findById_shouldBeNull_ifBookIdDoseNotExist() {

        when(bookRepository.getOne(1)).thenReturn(null);
        assertNull(bookService.findById(1));
    }

    @Test
    public void save_happyPath() {

        Book book = new Book();
        bookService.save(book);
        verify(bookRepository).saveAndFlush(book);
    }

    @Test
    public void decreaseStock_happyPath() {

        Book book = createBook(1, 1);
        when(bookRepository.getOne(book.getId())).thenReturn(book);
        bookService.decreaseStock(book.getId());
        verify(bookRepository).saveAndFlush(book);
        assertEquals(0, (int) book.getStock());
    }

    @Test
    public void decreaseStock__shouldBeZero_ifStockIsZero() {

        Book book = createBook(1, 0);
        when(bookRepository.getOne(book.getId())).thenReturn(book);
        bookService.decreaseStock(book.getId());
        verify(bookRepository).saveAndFlush(book);
        assertEquals(0, (int) book.getStock());
    }

    @Test
    public void increaseStock_happyPath() {

        Book book = createBook(1, 1);
        when(bookRepository.getOne(book.getId())).thenReturn(book);
        bookService.increaseStock(book.getId());
        verify(bookRepository).saveAndFlush(book);
        assertEquals(2, (int) book.getStock());
    }

    private Book createBook(Integer bookId, Integer stock) {

        Book book = new Book();
        book.setId(bookId);
        book.setStock(stock);
        return book;
    }
}
