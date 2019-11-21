package com.lxc.utils;

import com.lxc.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class PageUtilsTest {

    @InjectMocks
    private PageUtils pageUtils;

    @Test
    public void getPageFromList_happyPath() {

        Pageable pageable = PageRequest.of(0, 10, new Sort(Sort.Direction.ASC, "id"));
        List<Book> books = List.of(Book.builder().id(1).build());
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), books.size());
        PageImpl<Book> page = new PageImpl<>(books.subList(start, end), pageable, books.size());

        assertThat(pageUtils.getPageFromList(books, pageable)).isEqualTo(page);
    }
}
