package com.lxc.service.impl;

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

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    private final int PAGE_SIZE = 10;

    private final int PAGE_NUM = 0;

    private Pageable pageable = PageRequest.of(PAGE_NUM, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void findAllByPage_happyPath() {

        Page mockedPage = mock(Page.class);
        when(bookRepository.findAll(pageable)).thenReturn(mockedPage);
        assertThat(bookService.findAllByPage(PAGE_NUM), is(mockedPage));
    }

    @Test
    public void findAllByPage_shouldBeNull_ifNoBooks() {

        when(bookRepository.findAll(pageable)).thenReturn(null);
        assertThat(bookService.findAllByPage(PAGE_NUM), is(nullValue()));
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

        Page mockedPage = mock(Page.class);
        when(bookRepository.findByBookName("abc", pageable)).thenReturn(mockedPage);
        assertThat(bookService.findByCondition("name", "abc", PAGE_NUM), is(mockedPage));
    }

    @Test
    public void findByName_shouldBeNull_ifBookNameDoesNotExist() {

        when(bookRepository.findByBookName("abc", pageable)).thenReturn(null);
        assertThat(bookService.findByCondition("name", "abc", PAGE_NUM), is(nullValue()));
    }

    @Test
    public void findByAuthor_happyPath() {

        Page mockedPage = mock(Page.class);
        when(bookRepository.findByAuthor("abc", pageable)).thenReturn(mockedPage);
        assertThat(bookService.findByCondition("author", "abc", PAGE_NUM), is(mockedPage));
    }

    @Test
    public void findByAuthor_shouldBeNull_ifAuthorDoesNotExist() {

        when(bookRepository.findByAuthor("abc", pageable)).thenReturn(null);
        assertThat(bookService.findByCondition("author", "abc", PAGE_NUM), is(nullValue()));
    }

    @Test
    public void findByIsbn_happyPath() {

        Page mockedPage = mock(Page.class);
        when(bookRepository.findByIsbn("123", pageable)).thenReturn(mockedPage);
        assertThat(bookService.findByCondition("isbn", "123", PAGE_NUM), is(mockedPage));
    }

    @Test
    public void findByIsbn_shouldBeNull_ifIsbnDoesNotExist() {

        when(bookRepository.findByIsbn("123", pageable)).thenReturn(null);
        assertThat(bookService.findByCondition("isbn", "123", PAGE_NUM), is(nullValue()));
    }

    @Test
    public void setStatus_happyPath() {

        Book actual = new Book();
        when(bookRepository.getOne(1)).thenReturn(actual);
        bookService.setStatus("AVAILABLE", 1);
        verify(bookRepository).saveAndFlush(actual);
        assertThat(actual.getStatus(), equalTo("AVAILABLE"));
    }

    @Test
    public void findById_happyPath() {

        Book expected = new Book();
        when(bookRepository.getOne(1)).thenReturn(expected);
        Book actual = bookService.findById(1);
        assertThat(actual, is(expected));
    }

    @Test
    public void findById_shouldBeNull_ifBookIdDoesNotExist() {

        when(bookRepository.getOne(1)).thenReturn(null);
        assertThat(bookService.findById(1), is(nullValue()));
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
        assertThat(book.getStock(), is(0));
    }

    @Test
    public void decreaseStock_shouldBeZero_ifStockIsZero() {

        Book book = createBook(1, 0);
        when(bookRepository.getOne(book.getId())).thenReturn(book);
        bookService.decreaseStock(book.getId());
        verify(bookRepository).saveAndFlush(book);
        assertThat(book.getStock(), is(0));
    }

    @Test
    public void increaseStock_happyPath() {

        Book book = createBook(1, 1);
        when(bookRepository.getOne(book.getId())).thenReturn(book);
        bookService.increaseStock(book.getId());
        verify(bookRepository).saveAndFlush(book);
        assertThat(book.getStock(), is(2));
    }

    private Book createBook(Integer bookId, Integer stock) {

        return Book.builder().id(bookId).stock(stock).build();
    }
}
