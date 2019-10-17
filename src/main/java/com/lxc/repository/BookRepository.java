package com.lxc.repository;

import com.lxc.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * 书的dao
 */
public interface BookRepository extends JpaRepository<Book,Integer> {

    List<Book> findByBookName(String bookName);
}
