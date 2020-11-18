package com.hyeonah.hellojpa.service;


import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.hyeonah.hellojpa.domain.Address;
import com.hyeonah.hellojpa.domain.Member;
import com.hyeonah.hellojpa.domain.Order;
import com.hyeonah.hellojpa.domain.OrderStatus;
import com.hyeonah.hellojpa.domain.item.Book;
import com.hyeonah.hellojpa.domain.item.Item;
import com.hyeonah.hellojpa.repository.OrderRepository;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hyeonahlee on 2020-11-17.
 */
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("상품 주문 테스트")
    public void createOrder() {
        // given
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 함", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());
    }

    @Test
    @DisplayName("주문 취소")
    public void cancelOrder() {
        // given
        Member member = createMember();
        Book item = createBook("JPAJPA", 10000, 10);
        
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        
        assertEquals("주문 취소시 상태는 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 해당 재고만큼 재고가 증가", 10, item.getStockQuantity());
    }

    @Test()
    @DisplayName("상품 주문 재고 수량초과 테스트")
    public void createOrder_stockQuantity() {
        // given
        Member member = createMember();
        Item item = createBook("test", 10000, 10);

        int orderCount = 11;

        // TODO: 20201118// junit5 expected Exception!
        // when
//        assertThrows(() -> {
//            orderService.order(member.getId(), item.getId(), orderCount);
//        });

        // then
        fail("재고 수량 부족 예외가 발생!");
    }

    public Member createMember() {
        Member member = new Member();
        member.setName("회원 1");
        member.setAddress(new Address("서울", "강서구", "123-123"));
        em.persist(member);
        return member;
    }

    public Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}
