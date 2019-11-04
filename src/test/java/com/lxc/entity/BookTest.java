package com.lxc.entity;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import com.lxc.testUtils.DateUtils;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class BookTest {

    @Test
    public void getPublishDate_happyPath() throws ParseException {

        Date date = DateUtils.DateConvertor("2019-10-29");
        Book book = Book.builder().publishDate(date).build();
        assertThat(book.getPublishDate(), is(date));
    }

    @Test
    public void increaseStock_happyPath() {

        Book book = Book.builder().stock(1).build();
        book.increaseStock();
        assertThat(book.getStock(), is(2));
    }

    @Test
    public void decreaseStock_happyPath() {

        Book book = Book.builder().stock(1).build();
        book.decreaseStock();
        assertThat(book.getStock(), is(0));
    }

    @Test
    public void decreaseStock_shouldBeZero_ifStockIsZero() {

        Book book = Book.builder().stock(0).build();
        book.decreaseStock();
        assertThat(book.getStock(), is(0));
    }
}
