package com.lxc.entity;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import com.lxc.testUtils.DateUtils;

import static org.assertj.core.api.Assertions.*;

public class BookTest {

    @Test
    public void getBookName_happyPath() {

        Book book = Book.builder().bookName("abc").build();
        assertThat(book.getBookName()).isEqualTo("abc");
    }

    @Test
    public void getAuthor_happyPath() {

        Book book = Book.builder().author("abc").build();
        assertThat(book.getAuthor()).isEqualTo("abc");
    }

    @Test
    public void getIsbn_happyPath() {

        Book book = Book.builder().isbn("123").build();
        assertThat(book.getIsbn()).isEqualTo("123");
    }

    @Test
    public void getIntro_happyPath() {

        Book book = Book.builder().intro("abc").build();
        assertThat(book.getIntro()).isEqualTo("abc");
    }

    @Test
    public void getPrice_happyPath() {

        Book book = Book.builder().price(0.0).build();
        assertThat(book.getPrice()).isEqualTo(0.0);
    }

    @Test
    public void getStock_happyPath() {

        Book book = Book.builder().stock(100).build();
        assertThat(book.getStock()).isEqualTo(100);
    }

    @Test
    public void getStatus_happyPath() {

        Book book = Book.builder().status("PAID").build();
        assertThat(book.getStatus()).isEqualTo("PAID");
    }

    @Test
    public void getPublishDate_happyPath() throws ParseException {

        Date date = DateUtils.parse("2019-10-29");
        Book book = Book.builder().publishDate(date).build();
        assertThat(book.getPublishDate()).isEqualTo(date);
    }

    @Test
    public void increaseStock_happyPath() {

        Book book = Book.builder().stock(1).build();
        book.increaseStock();
        assertThat(book.getStock()).isEqualTo(2);
    }

    @Test
    public void decreaseStock_happyPath() {

        Book book = Book.builder().stock(1).build();
        book.decreaseStock();
        assertThat(book.getStock()).isEqualTo(0);
    }

    @Test
    public void decreaseStock_shouldBeZero_ifStockIsZero() {

        Book book = Book.builder().stock(0).build();
        book.decreaseStock();
        assertThat(book.getStock()).isEqualTo(0);
    }

    @Test
    public void toString_happyPath() {

        assertThat(Book.builder().toString()).isEqualTo("Book.BookBuilder(id=null, bookName=null, author=null, " +
                "isbn=null, publishDate=null, intro=null, price=null, stock=null, status=null)");
    }
}
