package com.lxc.entity;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import com.lxc.testUtils.StringToDate;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class BookTest {

    @Test
    public void getPublishDate_happyPath() throws ParseException {

        Book book = new Book();
        Date date = StringToDate.stringToDate("2019-10-29");
        book.setPublishDate(date);
        assertThat(book.getPublishDate(), is(date));
    }

    @Test
    public void increaseStock_happyPath() {

        Book book = new Book();
        book.setStock(1);
        book.increaseStock();
        assertThat(book.getStock(), is(2));
    }

    @Test
    public void decreaseStock_happyPath() {

        Book book = new Book();
        book.setStock(1);
        book.decreaseStock();
        assertThat(book.getStock(), is(0));
    }

    @Test
    public void decreaseStock_shouldBeZero_ifStockIsZero() {

        Book book = new Book();
        book.setStock(0);
        book.decreaseStock();
        assertThat(book.getStock(), is(0));
    }
}
