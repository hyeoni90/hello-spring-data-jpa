package com.hyeonah.hellojpa;

import com.hyeonah.hellojpa.domain.*;
import com.hyeonah.hellojpa.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * Created by hyeoni90 on 2021-02-25
 * 서버를 띄울 때 데이터 초기화
 */
@Component
@RequiredArgsConstructor
public class initDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            final Member member = createMember("memberA", "seoul", "11AA", "1111");
            em.persist(member);

            final Book book1 = createBook("JPA1", 100000, 100);
            em.persist(book1);

            final Book book2 = createBook("Spring Data JPA1", 200000, 100);
            em.persist(book2);

            final OrderItem orderItem1 = OrderItem.createOrderItem(book1, 200000, 2);
            final OrderItem orderItem2 = OrderItem.createOrderItem(book2, 300000, 3);

            final Order order = createDelivery(member, orderItem1, orderItem2);
            em.persist(order);
        }

        private Book createBook(final String name, final int price, final int stockQuantity) {
            final Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        public void dbInit2() {
            final Member member = createMember("memberB", "busan", "11BB", "333");
            em.persist(member);

            final Book book1 = createBook("JPA2", 100000, 100);
            em.persist(book1);

            final Book book2 = createBook("Spring Data JPA2", 200000, 100);
            em.persist(book2);

            final OrderItem orderItem1 = OrderItem.createOrderItem(book1, 100000, 1);
            final OrderItem orderItem2 = OrderItem.createOrderItem(book2, 200000, 2);

            final Order order = createDelivery(member, orderItem1, orderItem2);
            em.persist(order);
        }

        private Order createDelivery(final Member member, final OrderItem orderItem1, final OrderItem orderItem2) {
            final Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return Order.createOrder(member, delivery, orderItem1, orderItem2);
        }

        private Member createMember(final String name, final String city, final String street, final String zipCode) {
            final Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipCode));
            return member;
        }
    }
}
