package com.lxc.repository;

import com.lxc.entity.Book;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import static org.hamcrest.Matchers.*;


public class BookRepositoryTest extends BaseRepositoryTest {

    private Book book = new Book("abc", "abc", "123");

    @Test
    public void findByBookName_happyPath() {

        insertTestBook(book);
        Page<Book> page = bookRepository.findByBookName("abc", pageable);
        Assert.assertThat(page.getContent(), contains(book));
    }

    @Test
    public void findByAuthor_happyPath() {

        insertTestBook(book);
        Page<Book> page = bookRepository.findByAuthor("abc", pageable);
        Assert.assertThat(page.getContent(), contains(book));
    }

    @Test
    public void findByIsbn_happyPath() {

        insertTestBook(book);
        Page<Book> page = bookRepository.findByIsbn("123", pageable);
        Assert.assertThat(page.getContent(), contains(book));
    }

    private void insertTestBook(Book book) {

        bookRepository.saveAndFlush(book);
    }
}
