package com.lxc.repository;

import com.lxc.entity.Book;
import org.junit.Test;
import org.springframework.data.domain.Page;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;


public class BookRepositoryTest extends BaseRepositoryTes {

    @Test
    public void findByBookName_happyPath() {

        Book book = insertBookWithName("abc");
        Page<Book> page = bookRepository.findByBookName("abc", pageable);
        assertThat(page.getContent(), hasItem(book));
    }

    @Test
    public void findByBookName_shouldBeEmpty_ifBookDoesNotExist() {

        insertBookWithName("abc");
        Page<Book> page = bookRepository.findByBookName("def", pageable);
        assertThat(page.getContent().size(), is(0));
    }

    @Test
    public void findByAuthor_happyPath() {

        Book book = insertBookWithAuthor("abc");
        Page<Book> page = bookRepository.findByAuthor("abc", pageable);
        assertThat(page.getContent(), hasItem(book));
    }

    @Test
    public void findByAuthor_shouldBeEmpty_ifBookDoesNotExist() {

        insertBookWithAuthor("abc");
        Page<Book> page = bookRepository.findByAuthor("def", pageable);
        assertThat(page.getContent().size(), is(0));
    }

    @Test
    public void findByIsbn_happyPath() {

        Book book = insertBookWithIsbn("123");
        Page<Book> page = bookRepository.findByIsbn("123", pageable);
        assertThat(page.getContent(), hasItem(book));
    }

    @Test
    public void findByIsbn_shouldBeEmpty_ifBookDoesNotExist() {

        insertBookWithIsbn("123");
        Page<Book> page = bookRepository.findByIsbn("321", pageable);
        assertThat(page.getContent().size(), is(0));
    }

    private Book insertBookWithName(String bookName) {

        Book book = Book.builder().bookName(bookName).build();
        bookRepository.saveAndFlush(book);
        return book;
    }

    private Book insertBookWithAuthor(String author) {

        Book book = Book.builder().author(author).build();
        bookRepository.saveAndFlush(book);
        return book;
    }

    private Book insertBookWithIsbn(String isbn) {

        Book book = Book.builder().isbn(isbn).build();
        bookRepository.saveAndFlush(book);
        return book;
    }
}
