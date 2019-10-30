package com.lxc.repository;

import com.lxc.entity.Book;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import static org.hamcrest.Matchers.*;


public class BookRepositoryTest extends Base {

    private Book book;

    @Test
    public void findByBookName_happyPath() {

        insertTestBook("abc", null, null);
        Page<Book> page = bookRepository.findByBookName("abc", pageable);
        Assert.assertThat(page.getContent(), contains(book));
    }

    @Test
    public void findByBookName_shouldBeZeroResult_ifBookDoesNotExist() {

        insertTestBook("abc", null, null);
        Page<Book> page = bookRepository.findByBookName("def", pageable);
        Assert.assertEquals(0, page.getContent().size());
    }

    @Test
    public void findByAuthor_happyPath() {

        insertTestBook(null, "abc", null);
        Page<Book> page = bookRepository.findByAuthor("abc", pageable);
        Assert.assertThat(page.getContent(), contains(book));
    }

    @Test
    public void findByAuthor_shouldBeZeroResult_ifBookDoesNotExist() {

        insertTestBook(null, "abc", null);
        Page<Book> page = bookRepository.findByAuthor("def", pageable);
        Assert.assertEquals(0, page.getContent().size());
    }

    @Test
    public void findByIsbn_happyPath() {

        insertTestBook(null, null, "123");
        Page<Book> page = bookRepository.findByIsbn("123", pageable);
        Assert.assertThat(page.getContent(), contains(book));
    }

    @Test
    public void findByIsbn_shouldBeZeroResult_ifBookDoesNotExist() {

        insertTestBook(null, null, "123");
        Page<Book> page = bookRepository.findByIsbn("321", pageable);
        Assert.assertEquals(0, page.getContent().size());
    }

    private void insertTestBook(String bookName, String author, String isbn) {

        book = new Book(bookName, author, isbn);
        bookRepository.saveAndFlush(book);
    }
}
