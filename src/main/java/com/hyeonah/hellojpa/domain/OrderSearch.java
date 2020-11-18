package com.hyeonah.hellojpa.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by hyeonahlee on 2020-11-18.
 */
@Getter
@Setter
public class OrderSearch {

    private String memberName; // 회원 이름
    private OrderStatus orderStatus; // 주문 상태 [ORDER, CANCEL]
}
