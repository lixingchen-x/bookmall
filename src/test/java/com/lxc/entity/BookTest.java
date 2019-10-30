package com.lxc.entity;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import com.lxc.testUtils.StringToDate;

import static org.junit.Assert.assertEquals;

public class BookTest {

    private Book book = new Book();

    @Test
    public void getBookName_happyPath() {

        book.setBookName("abc");
        assertEquals("abc", book.getBookName());
    }

    @Test
    public void getAuthor_happyPath() {

        book.setAuthor("abc");
        assertEquals("abc", book.getAuthor());
    }

    @Test
    public void getIsbn_happyPath() {

        book.setIsbn("123456");
        assertEquals("123456", book.getIsbn());
    }

    @Test
    public void getPublishDate_happyPath() throws ParseException {

        Date date = StringToDate.stringToDate("2019-10-29");
        book.setPublishDate(date);
        assertEquals(date, book.getPublishDate());
    }

    @Test
    public void getIntro_happyPath() {

        book.setIntro("abc");
        assertEquals("abc", book.getIntro());
    }

    @Test
    public void getPrice_happyPath() {

        book.setPrice(0.0);
        assertEquals(0.0, book.getPrice(), 0.0);
    }

    @Test
    public void getStock_happyPath() {

        book.setStock(100);
        assertEquals(100, (int) book.getStock());
    }

    @Test
    public void getStatus_happyPath() {

        book.setStatus("PAID");
        assertEquals("PAID", book.getStatus());
    }

    @Test
    public void increaseStock_happyPath() {

        Book book = new Book();
        book.setStock(1);
        book.increaseStock();
        assertEquals(2, (int)book.getStock());
    }

    @Test
    public void decreaseStock_happyPath() {

        Book book = new Book();
        book.setStock(1);
        book.decreaseStock();
        assertEquals(0, (int)book.getStock());
    }

    @Test
    public void decreaseStock_shouldBeZero_ifStockIsZero() {

        Book book = new Book();
        book.setStock(0);
        book.decreaseStock();
        assertEquals(0, (int)book.getStock());
    }
}
