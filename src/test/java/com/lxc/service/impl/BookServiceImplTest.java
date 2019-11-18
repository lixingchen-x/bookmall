package com.lxc.service.impl;

import com.lxc.constants.AddResult;
import com.lxc.constants.BookStatus;
import com.lxc.entity.Book;
import com.lxc.exception.StockNotEnoughException;
import com.lxc.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    private final int PAGE_SIZE = 10;

    private final int PAGE_NUM = 0;

    private Pageable pageable = PageRequest.of(PAGE_NUM, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Value("${file.upload.path}")
    private String filePath;

    @Test
    public void findAllByPage_happyPath() {

        Page mockedPage = mock(Page.class);
        when(bookRepository.findAll(pageable)).thenReturn(mockedPage);

        assertThat(bookService.findAllByPage(PAGE_NUM)).isEqualTo(mockedPage);
    }

    @Test
    public void findAllByPage_shouldReturnNull_ifNoBooks() {

        when(bookRepository.findAll(pageable)).thenReturn(null);

        assertThat(bookService.findAllByPage(PAGE_NUM)).isNull();
    }

    @Test
    public void update_happyPath() {

        Book newBook = Book.builder().id(1).build();
        Book temp = mock(Book.class);
        when(bookService.findById(1)).thenReturn(temp);

        bookService.update(newBook);

        verify(bookRepository).saveAndFlush(temp);
    }

    @Test
    public void update_shouldDoNothing_ifBookIdDoesNotExist() {

        Book newBook = Book.builder().id(1).build();
        when(bookService.findById(1)).thenReturn(null);

        bookService.update(newBook);

        verify(bookRepository, never()).saveAndFlush(any());
    }

    @Test
    public void deleteById_happyPath() {

        when(bookService.findById(1)).thenReturn(mock(Book.class));

        bookService.deleteById(1);

        verify(bookRepository).deleteById(1);
    }

    @Test
    public void deleteById_shouldDoNothing_ifBookIdDoesNotExist() {

        when(bookService.findById(1)).thenReturn(null);

        bookService.deleteById(1);

        verify(bookRepository, never()).deleteById(1);
    }

    @Test
    public void findByName_happyPath() {

        Page mockedPage = mock(Page.class);
        when(bookRepository.findByBookName("abc", pageable)).thenReturn(mockedPage);

        assertThat(bookService.findPageableByCondition("name", "abc", PAGE_NUM)).isEqualTo(mockedPage);
    }

    @Test
    public void findByName_shouldReturnNull_ifBookNameDoesNotExist() {

        when(bookRepository.findByBookName("abc", pageable)).thenReturn(null);

        assertThat(bookService.findPageableByCondition("name", "abc", PAGE_NUM)).isNull();
    }

    @Test
    public void findByAuthor_happyPath() {

        Page mockedPage = mock(Page.class);
        when(bookRepository.findByAuthor("abc", pageable)).thenReturn(mockedPage);

        assertThat(bookService.findPageableByCondition("author", "abc", PAGE_NUM)).isEqualTo(mockedPage);
    }

    @Test
    public void findByAuthor_shouldReturnNull_ifAuthorDoesNotExist() {

        when(bookRepository.findByAuthor("abc", pageable)).thenReturn(null);

        assertThat(bookService.findPageableByCondition("author", "abc", PAGE_NUM)).isNull();
    }

    @Test
    public void findByIsbn_happyPath() {

        Book book = mock(Book.class);
        when(bookRepository.findByIsbn("123")).thenReturn(book);

        assertThat(bookService.bookToBookPage(book)).isEqualTo(bookService.findByIsbn("123"));
    }

    @Test
    public void findByIsbn_shouldReturnEmptyPage_ifIsbnDoesNotExist() {

        when(bookRepository.findByIsbn("123")).thenThrow(EntityNotFoundException.class);

        assertThat(bookService.bookToBookPage(null)).isEqualTo(bookService.findByIsbn("123"));
    }

    @Test
    public void setStatus_happyPath() {

        Book actual = new Book();
        when(bookService.findById(1)).thenReturn(actual);

        bookService.setStatus(BookStatus.AVAILABLE, 1);

        verify(bookRepository).saveAndFlush(actual);
        assertThat(actual.getStatus()).isEqualTo(BookStatus.AVAILABLE);
    }

    @Test
    public void setStatus_shouldDoNothing_ifBookIdDoesNotExist() {

        when(bookService.findById(1)).thenReturn(null);

        bookService.setStatus(BookStatus.AVAILABLE, 1);

        verify(bookRepository, never()).saveAndFlush(null);
    }

    @Test
    public void findById_happyPath() {

        Book expected = new Book();
        when(bookRepository.getOne(1)).thenReturn(expected);

        assertThat(bookService.findById(1)).isEqualTo(expected);
    }

    @Test
    public void findById_shouldReturnNull_ifBookIdDoesNotExist() {

        when(bookRepository.getOne(1)).thenThrow(EntityNotFoundException.class);

        assertThat(bookService.findById(1)).isNull();
    }

    @Test
    public void addBook_happyPath() {

        Book book = Book.builder().isbn("123").build();
        when(bookRepository.findByIsbn("123")).thenReturn(null);

        assertThat(bookService.addBook(book)).isEqualTo(AddResult.SUCCESS);
        verify(bookRepository).saveAndFlush(book);
    }

    @Test
    public void addBook_shouldFail_ifBookExists() {

        Book book = Book.builder().isbn("123").build();
        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(book);

        assertThat(bookService.addBook(book)).isEqualTo(AddResult.FAIL);
    }

    @Test
    public void decreaseStock_happyPath() throws StockNotEnoughException {

        Book book = createBook(1, 1);
        when(bookService.findById(1)).thenReturn(book);

        bookService.decreaseStock(1, 1);

        verify(bookRepository).saveAndFlush(book);
        assertThat(book.getStock()).isEqualTo(0);
    }

    @Test
    public void decreaseStock_shouldDoNothing_ifBookIdDoesNotExist() throws StockNotEnoughException {

        when(bookService.findById(1)).thenReturn(null);

        bookService.decreaseStock(1, 1);

        verify(bookRepository, never()).saveAndFlush(null);
    }

    @Test(expected = StockNotEnoughException.class)
    public void decreaseStock_shouldCatchException_ifStockIsNotEnough() throws StockNotEnoughException {

        Book book = Book.builder().id(1).stock(0).build();
        when(bookService.findById(1)).thenReturn(book);

        bookService.decreaseStock(1, 1);

        verify(bookRepository, never()).saveAndFlush(book);
    }

    @Test
    public void increaseStock_happyPath() {

        Book book = createBook(1, 1);
        when(bookService.findById(1)).thenReturn(book);

        bookService.increaseStock(1, 1);

        verify(bookRepository).saveAndFlush(book);
        assertThat(book.getStock()).isEqualTo(2);
    }

    @Test
    public void increaseStock_shouldDoNothing_ifBookIdDoesNotExist() {

        when(bookService.findById(1)).thenReturn(null);

        bookService.increaseStock(1, 1);

        verify(bookRepository, never()).saveAndFlush(null);
    }

    @Test
    public void saveUrl_happyPath() {

        Book book = Book.builder().id(1).build();
        when(bookService.findById(1)).thenReturn(book);

        bookService.saveUrl(1, "a");

        assertThat(book.getImgUrl()).isEqualTo("a");
        verify(bookRepository).saveAndFlush(book);
    }

    @Test
    public void saveUrl_shouldDoNothing_ifBookIdDoesNotExist() {

        when(bookService.findById(1)).thenReturn(null);

        bookService.saveUrl(1, "a");

        verify(bookRepository, never()).saveAndFlush(null);
    }

    private Book createBook(Integer bookId, Integer stock) {

        return Book.builder().id(bookId).stock(stock).build();
    }
}
