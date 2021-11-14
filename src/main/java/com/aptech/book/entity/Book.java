//package com.aptech.book.entity;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Table(name = "book")
//public class Book {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", unique = true, nullable = false)
//    private int id;
//
//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;
//
//    @Column(name = "title", unique = true, nullable = false, length = 128)
//    private String title;
//
//    @Column(name = "author", nullable = false, length = 64)
//    private String author;
//
//    @Column(name = "description", nullable = false, length = 16777215)
//    private String description;
//
//    @Column(name = "isbn", nullable = false, length = 15)
//    private String isbn;
//
//    @Column(name = "price", nullable = false, precision = 12, scale = 0)
//    private float price;
//
//    @Column(name = "date_created")
//    @CreationTimestamp
//    private Date dateCreated;
//
//    @Column(name = "last_updated")
//    @UpdateTimestamp
//    private Date lastUpdated;
//
//    @Column(name = "image_name", nullable = true)
//    private String imageName;
//
//    public Book() {
//    }
//
//    @Override
//    public String toString() {
//        return "Book{" +
//                "id=" + id +
//                ", category=" + category +
//                ", title='" + title + '\'' +
//                ", author='" + author + '\'' +
//                ", description='" + description + '\'' +
//                ", isbn='" + isbn + '\'' +
//                ", price=" + price +
//                ", dateCreated=" + dateCreated +
//                ", lastUpdated=" + lastUpdated +
//                ", imageName='" + imageName + '\'' +
//                '}';
//    }
//}
