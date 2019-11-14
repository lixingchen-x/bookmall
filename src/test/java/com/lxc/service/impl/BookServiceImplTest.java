package com.lxc.service.impl;

import com.lxc.constants.AddResults;
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
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import java.io.File;
import java.io.IOException;

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
    public void update_shouldThrowException_ifBookIdDoesNotExist() {

        Book newBook = Book.builder().id(1).build();
        when(bookService.findById(1)).thenThrow(EntityNotFoundException.class);

        try {
            bookService.update(newBook);
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("BOOK_DOES_NOT_EXIST");
        }
    }

    @Test
    public void deleteById_happyPath() {

        bookService.deleteById(1);

        verify(bookRepository).deleteById(1);
    }

    @Test
    public void deleteById_shouldThrowException_ifBookIdDoesNotExist() {

        when(bookService.findById(1)).thenThrow(EntityNotFoundException.class);

        try {
            bookService.deleteById(1);
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("BOOK_DOES_NOT_EXIST");
        }
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
    public void setStatus_shouldThrowException_ifBookIdDoesNotExist() {

        when(bookService.findById(1)).thenThrow(EntityNotFoundException.class);

        try {
            bookService.setStatus(BookStatus.AVAILABLE, 1);
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("BOOK_DOES_NOT_EXIST");
        }
    }

    @Test
    public void findById_happyPath() {

        Book expected = new Book();
        when(bookRepository.getOne(1)).thenReturn(expected);

        Book actual = bookService.findById(1);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findById_shouldThrowException_ifBookIdDoesNotExist() {

        when(bookRepository.getOne(1)).thenThrow(EntityNotFoundException.class);

        try {
            bookService.findById(1);
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("BOOK_DOES_NOT_EXIST");
        }
    }

    @Test
    public void addBook_happyPath() {

        Book book = Book.builder().isbn("123").build();
        when(bookRepository.findByIsbn("123")).thenReturn(null);

        assertThat(bookService.addBook(book)).isEqualTo(AddResults.SUCCESS);
        verify(bookRepository).saveAndFlush(book);
    }

    @Test
    public void addBook_shouldFail_ifBookExists() {

        Book book = Book.builder().isbn("123").build();
        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(book);

        assertThat(bookService.addBook(book)).isEqualTo(AddResults.FAIL);
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
    public void decreaseStock_shouldThrowException_ifBookIdDoesNotExist() throws StockNotEnoughException {

        when(bookService.findById(1)).thenThrow(EntityNotFoundException.class);

        try {
            bookService.decreaseStock(1, 1);
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("BOOK_DOES_NOT_EXIST");
        }
    }

    @Test
    public void decreaseStock_shouldCatchException_ifStockIsNotEnough() {

        Book book = Book.builder().id(1).stock(0).build();
        when(bookService.findById(1)).thenReturn(book);

        try {
            bookService.decreaseStock(1, 1);
        } catch (StockNotEnoughException e) {
            assertThat(e.getMessage()).isEqualTo("STOCK_IS_NOT_ENOUGH");
        }
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
    public void increaseStock_shouldCatchException_ifBookIdDoesNotExist() {

        when(bookService.findById(1)).thenThrow(EntityNotFoundException.class);

        try {
            bookService.increaseStock(1, 1);
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("BOOK_DOES_NOT_EXIST");
        }
    }

    @Test
    public void uploadImg_happyPath() throws IOException {

        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("a.jpg");
        File destination = new File(filePath, "a.jpg");

        bookService.uploadImg(file);

        verify(file).transferTo(destination);
    }

    @Test
    public void uploadImg_shouldThrowException_ifIOFails() throws IOException {

        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("a.jpg");
        File destination = new File(filePath, "a.jpg");
        IOException exception = mock(IOException.class);
        doThrow(exception).when(file).transferTo(destination);

        bookService.uploadImg(file);
    }

    @Test
    public void saveUrl_happyPath() {

        Book book = Book.builder().id(1).build();
        when(bookRepository.getOne(1)).thenReturn(book);

        bookService.saveUrl(1, "a");

        assertThat(book.getImgUrl()).isEqualTo("a");
        verify(bookRepository).saveAndFlush(book);
    }

    private Book createBook(Integer bookId, Integer stock) {

        return Book.builder().id(bookId).stock(stock).build();
    }
}
