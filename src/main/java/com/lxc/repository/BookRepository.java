package com.lxc.repository;

import com.lxc.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 书的dao
 */
public interface BookRepository extends JpaRepository<Book, Integer>{

    Page<Book> findByBookName(String bookName, Pageable pageable);

    Page<Book> findByAuthor(String author, Pageable pageable);

    Page<Book> findByIsbn(String isbn, Pageable pageable);
}
