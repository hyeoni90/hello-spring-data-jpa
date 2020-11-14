package com.hyeonah.hellospringdatajpa.domain;

import javax.persistence.*;

import com.hyeonah.hellospringdatajpa.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hyeonahlee on 2020-11-13.
 */
@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; //  주문 수량
}
