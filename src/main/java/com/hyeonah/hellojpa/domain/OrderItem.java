package com.hyeonah.hellojpa.domain;

import javax.persistence.*;

import com.hyeonah.hellojpa.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import static javax.persistence.FetchType.LAZY;

/**
 * Created by hyeonahlee on 2020-11-13.
 */
@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; //  주문 수량

}
