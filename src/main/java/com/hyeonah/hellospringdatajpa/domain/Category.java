package com.hyeonah.hellospringdatajpa.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.hyeonah.hellospringdatajpa.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hyeonahlee on 2020-11-13.
 */
@Entity
@Getter
@Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name ="category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}
