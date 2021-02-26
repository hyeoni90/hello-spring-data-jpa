package com.hyeonah.hellojpa.api.v1;

import com.hyeonah.hellojpa.domain.Order;
import com.hyeonah.hellojpa.domain.OrderSearch;
import com.hyeonah.hellojpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by hyeoni90 on 2021-02-25
 *
 * <p>
 * XToONE (OneToOne, ManyToOne) 의 성능 최적화
 * <p>
 *
 * Order
 * Order > Member
 * Order > Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    /**
     * - Hibernate5Module 등록, LAZY=null 처리
     * - 양방향 관계 문제 > @JsonIgnore
     *
     * @return
     */
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        final List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        for (final Order order : orders) {
            order.getMember().getName();    // Lazy 강제 초기화
            order.getDelivery().getAddress();   //Lazy 강제 초기화
        }
        return orders;
    }
}
