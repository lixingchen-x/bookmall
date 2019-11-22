package com.lxc.repository;

import com.lxc.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer>{

    /**
     * retrieve books by bookName
     *
     * @param bookName
     * @param pageable
     * @return
     */
    Page<Book> findByBookName(String bookName, Pageable pageable);

    /**
     * retrieve books by author
     *
     * @param author
     * @param pageable
     * @return
     */
    Page<Book> findByAuthor(String author, Pageable pageable);

    /**
     * retrieve book by isbn
     *
     * @param isbn
     * @return
     */
    Book findByIsbn(String isbn);
}
