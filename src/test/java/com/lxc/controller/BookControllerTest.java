package com.lxc.controller;

import com.lxc.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll_happyPath() throws Exception {

        Page mockedPage = mock(Page.class);
        when(bookService.findAllByPage(0)).thenReturn(mockedPage);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/book/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("bookManagement/bookList.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("bookPage", mockedPage))
                .andExpect(MockMvcResultMatchers.model().attribute("condition", "all"))
                .andReturn();
    }

    @Test
    public void findBookByName_happyPath() {

        when(bookService.findPageableByCondition("name", "a", 0)).thenReturn(mock(Page.class));

        assertThat(bookController.findBookByName("a", 0, mock(Model.class)))
                .isEqualTo("bookManagement/bookList.html");
        verify(bookService).findPageableByCondition("name", "a", 0);
    }

    @Test
    public void findBookByAuthor_happyPath() {

        when(bookService.findPageableByCondition("author", "a", 0)).thenReturn(mock(Page.class));

        assertThat(bookController.findBookByAuthor("a", 0, mock(Model.class)))
                .isEqualTo("bookManagement/bookList.html");
        verify(bookService).findPageableByCondition("author", "a", 0);
    }

    @Test
    public void findBookByIsbn_happyPath() {

        when(bookService.findByIsbn("a")).thenReturn(mock(Page.class));

        assertThat(bookController.findBookByIsbn("a", 0, mock(Model.class)))
                .isEqualTo("bookManagement/bookList.html");
        verify(bookService).findByIsbn("a");
    }
}
