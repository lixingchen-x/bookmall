package com.lxc.repository;

import com.lxc.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 书的dao
 */
public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor {

    List<Book> findByBookName(String bookName);

    List<Book> findByAuthor(String author);

    List<Book> findByIsbn(String isbn);
}
