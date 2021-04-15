package com.hyeonah.hellojpa.application.dto;

import com.hyeonah.hellojpa.domain.Address;
import com.hyeonah.hellojpa.domain.Order;
import com.hyeonah.hellojpa.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderDto(final Order order) {
        orderId = order.getId();
        name = order.getMember().getName(); // LAZY 초기화
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress(); // LAZY 초기화 
    }
}
