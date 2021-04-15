package com.hyeonah.hellojpa.api.v3;

import com.hyeonah.hellojpa.application.dto.OrderDto;
import com.hyeonah.hellojpa.domain.Order;
import com.hyeonah.hellojpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiV3Controller {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV2() {
        final List<Order> orders = orderRepository.findAllWithMemberAndDelivery();
        return orders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }
}
