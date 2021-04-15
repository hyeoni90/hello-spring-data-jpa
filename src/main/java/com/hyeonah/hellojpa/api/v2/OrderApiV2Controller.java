package com.hyeonah.hellojpa.api.v2;

import com.hyeonah.hellojpa.application.dto.OrderDto;
import com.hyeonah.hellojpa.domain.OrderSearch;
import com.hyeonah.hellojpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiV2Controller {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        // ORDER 2개
        // N + 1 문제 (회원 N +배송 N) 쿼리가 총 5번 실행
        return orderRepository.findAllByString(new OrderSearch()).stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }
}
