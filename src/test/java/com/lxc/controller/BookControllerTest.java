package com.lxc.controller;

import com.lxc.constants.AddResults;
import com.lxc.constants.BookStatus;
import com.lxc.entity.Book;
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
    public void toAddBook_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/book/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("bookManagement/addBook.html"))
                .andReturn();
    }

    @Test
    public void addBook_happyPath() throws Exception {

        when(bookService.addBook(any())).thenReturn(AddResults.SUCCESS);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/book/add"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrlTemplate("/book/books"))
                .andReturn();
    }

    @Test
    public void addBook_shouldFail_ifBookExists() throws Exception {

        when(bookService.addBook(any())).thenReturn(AddResults.FAIL);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/book/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bookManagement/addBook.html"))
                .andExpect(MockMvcResultMatchers.request().attribute("addBook", "新增图书失败，图书已存在！"))
                .andReturn();
    }

    @Test
    public void toUpdateBook_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/book/update/{bookId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("bookManagement/editBook.html"))
                .andReturn();
    }

    @Test
    public void updateBook_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.put("/book/update"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrlTemplate("/book/books"))
                .andReturn();
    }

    @Test
    public void withdrawBook_happyPath() throws Exception {

        Book book = Book.builder().id(1).build();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/book/withdraw")
                .param("bookId", book.getId().toString()).param("page", String.valueOf(0)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/book/books"))
                .andReturn();
        verify(bookService).setStatus(BookStatus.WITHDRAW, book.getId());
    }

    @Test
    public void onSaleBook_happyPath() throws Exception {

        Book book = Book.builder().id(1).build();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/book/onSale")
                .param("bookId", book.getId().toString()).param("page", String.valueOf(0)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/book/books"))
                .andReturn();
        verify(bookService).setStatus(BookStatus.AVAILABLE, book.getId());
    }

    @Test
    public void deleteBook_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/book/delete/{bookId}", 1))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/book/books"))
                .andReturn();
        verify(bookService).deleteById(1);
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
