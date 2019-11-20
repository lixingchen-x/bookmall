package com.lxc.controller.admin;

import com.lxc.constants.BookStatusEnum;
import com.lxc.constants.ResultEnum;
import com.lxc.entity.Book;
import com.lxc.service.BookService;
import com.lxc.service.FileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class BookManageControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BookManageController bookManageController;

    @Mock
    private BookService bookService;

    @Mock
    private FileService fileService;

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(bookManageController).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void toAddBook_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/book/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("bookManagement/addBook.html"))
                .andReturn();
    }

    @Test
    public void addBook_happyPath() throws Exception {

        when(bookService.addBook(any())).thenReturn(ResultEnum.SUCCESS);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/book/add"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrlTemplate("/book/books"))
                .andReturn();
    }

    @Test
    public void addBook_shouldFail_ifBookExists() throws Exception {

        when(bookService.addBook(any())).thenReturn(ResultEnum.FAIL);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/book/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bookManagement/addBook.html"))
                .andExpect(MockMvcResultMatchers.request().attribute("addBook", "新增图书失败，图书已存在！"))
                .andReturn();
    }

    @Test
    public void toUpdateBook_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/book/update/{bookId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("bookManagement/editBook.html"))
                .andReturn();
    }

    @Test
    public void updateBook_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.put("/admin/book/update"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrlTemplate("/book/books"))
                .andReturn();
    }

    @Test
    public void withdrawBook_happyPath() throws Exception {

        Book book = Book.builder().id(1).build();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/book/withdraw")
                .param("bookId", book.getId().toString()).param("page", String.valueOf(0)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/book/books"))
                .andReturn();
        verify(bookService).setStatus(BookStatusEnum.WITHDRAW, book.getId());
    }

    @Test
    public void onSaleBook_happyPath() throws Exception {

        Book book = Book.builder().id(1).build();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/book/onSale")
                .param("bookId", book.getId().toString()).param("page", String.valueOf(0)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/book/books"))
                .andReturn();
        verify(bookService).setStatus(BookStatusEnum.AVAILABLE, book.getId());
    }

    @Test
    public void deleteBook_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/book/delete/{bookId}", 1))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/book/books"))
                .andReturn();
        verify(bookService).deleteById(1);
    }

    @Test
    public void toAddBookImage_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/book/upload"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("bookManagement/upload.html"))
                .andReturn();
    }

    @Test
    public void imageUpload_happyPath() throws IOException {

        String url = "any";
        MultipartFile file = mock(MultipartFile.class);
        when(fileService.upload(file)).thenReturn(url);

        assertThat(bookManageController.imageUpload(1, file, mock(Model.class))).isEqualTo("bookManagement/upload.html");
        verify(bookService).saveUrl(1, url);
    }

    @Test
    public void imageUpload_shouldFail_ifIOFailed() throws Exception {

        MockMultipartFile file = mock(MockMultipartFile.class);

        this.mockMvc.perform(MockMvcRequestBuilders
                .multipart("/admin/book/upload", 1)
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
