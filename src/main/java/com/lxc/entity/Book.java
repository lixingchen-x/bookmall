package com.lxc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 书的实体类
 */
@Entity
@Table(name = "book")
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
@Getter
@Setter
@NoArgsConstructor
public class Book extends BaseEntity implements Serializable {

    @Column(name = "book_name")
    private String bookName;

    @Column
    private String author;

    @Column
    private String isbn;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "publish_date")
    private Date publishDate;

    @Column
    private String intro;

    @Column
    private Double price;

    @Column
    private Integer stock;

    @Column(name = "book_status")
    private String status;

    @Builder
    public Book(Integer id, String bookName, String author, String isbn, Date publishDate, String intro, Double price, Integer stock, String status) {
        super(id);
        this.bookName = bookName;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.intro = intro;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }

    public void increaseStock(Integer increment) {

        this.stock += increment;
    }

    public void decreaseStock() {

        if (this.stock >= 1) {
            this.stock -= 1;
        }
    }
}
