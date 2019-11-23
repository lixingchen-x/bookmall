package com.lxc.repository;

import com.lxc.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findByBookName(String bookName, Pageable pageable);

    Page<Book> findByAuthor(String author, Pageable pageable);

    Book findByIsbn(String isbn);
}
