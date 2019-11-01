package com.lxc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    private String image;

    @Column
    private Double price;

    @Column
    private Integer stock;

    @Column(name = "book_status")
    private String status;

    public void increaseStock() {

        this.stock += 1;
    }

    public void decreaseStock() {

        if (this.stock >= 1) {
            this.stock -= 1;
        }
    }
}
