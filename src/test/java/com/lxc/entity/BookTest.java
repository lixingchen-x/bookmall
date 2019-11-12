package com.lxc.entity;

import com.lxc.constants.BookStatus;
import com.lxc.exception.StockNotEnoughException;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import com.lxc.testUtils.DateUtils;

import static org.assertj.core.api.Assertions.*;

public class BookTest {

    @Test
    public void getBookName_happyPath() {

        Book book = new Book();
        book.setBookName("abc");

        assertThat(book.getBookName()).isEqualTo("abc");
    }

    @Test
    public void getAuthor_happyPath() {

        Book book = new Book();
        book.setAuthor("abc");

        assertThat(book.getAuthor()).isEqualTo("abc");
    }

    @Test
    public void getIsbn_happyPath() {

        Book book = new Book();
        book.setIsbn("123");

        assertThat(book.getIsbn()).isEqualTo("123");
    }

    @Test
    public void getIntro_happyPath() {

        Book book = new Book();
        book.setIntro("abc");

        assertThat(book.getIntro()).isEqualTo("abc");
    }

    @Test
    public void getPrice_happyPath() {

        Book book = new Book();
        book.setPrice(0.0);

        assertThat(book.getPrice()).isEqualTo(0.0);
    }

    @Test
    public void getStock_happyPath() {

        Book book = new Book();
        book.setStock(100);

        assertThat(book.getStock()).isEqualTo(100);
    }

    @Test
    public void getStatus_happyPath() {

        Book book = new Book();
        book.setStatus(BookStatus.AVAILABLE.getMsg());

        assertThat(book.getStatus()).isEqualTo("AVAILABLE");
    }

    @Test
    public void getPublishDate_happyPath() throws ParseException {

        Date date = DateUtils.parse("2019-10-29");
        Book book = new Book();
        book.setPublishDate(date);

        assertThat(book.getPublishDate()).isEqualTo(date);
    }

    @Test
    public void increaseStock_happyPath() {

        Book book = Book.builder().stock(1).build();
        book.increaseStock(1);

        assertThat(book.getStock()).isEqualTo(2);
    }

    @Test
    public void decreaseStock_happyPath() throws StockNotEnoughException {

        Book book = Book.builder().stock(1).build();
        book.decreaseStock(1);

        assertThat(book.getStock()).isEqualTo(0);
    }

    @Test(expected = StockNotEnoughException.class)
    public void decreaseStock_shouldBeZero_ifStockIsZero() throws StockNotEnoughException {

        Book book = Book.builder().stock(0).build();
        book.decreaseStock(1);
    }

    @Test
    public void toString_happyPath() {

        assertThat(Book.builder().toString()).isEqualTo("Book.BookBuilder(id=null, bookName=null, author=null, " +
                "isbn=null, publishDate=null, intro=null, price=null, stock=null, status=null)");
    }

    @Test
    public void builder_happyPath() throws ParseException {

        Book book = Book.builder().id(1).bookName("a").isbn("123").stock(1).price(100.0).author("a")
                .intro("a").publishDate(DateUtils.parse("2019-10-29")).status(BookStatus.AVAILABLE.getMsg()).build();

        assertThat(book.getId()).isEqualTo(1);
        assertThat(book.getBookName()).isEqualTo("a");
        assertThat(book.getIsbn()).isEqualTo("123");
        assertThat(book.getStock()).isEqualTo(1);
        assertThat(book.getPrice()).isEqualTo(100.0);
        assertThat(book.getAuthor()).isEqualTo("a");
        assertThat(book.getIntro()).isEqualTo("a");
        assertThat(book.getPublishDate()).isEqualTo("2019-10-29");
        assertThat(book.getStatus()).isEqualTo("AVAILABLE");
    }
}
