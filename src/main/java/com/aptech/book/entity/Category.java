package com.aptech.book.entity;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String image;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, String image) {
        this.name = name;
        this.image = image;
    }

    @Transient
    public String getImagePath() {
        if (id == null || image == null) return "/images/image-thumbnail.png";

        return "/category-images/" + this.id + "/" + this.image;
    }
}
