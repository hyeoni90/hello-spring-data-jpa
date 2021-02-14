package com.hyeonah.hellojpa.controller;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by hyeoni90 on 2021-02-14
 */
@Getter
@Setter
public class BookForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;
}
