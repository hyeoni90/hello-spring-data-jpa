package com.hyeonah.hellojpa.service;

import com.hyeonah.hellojpa.domain.Delivery;
import com.hyeonah.hellojpa.domain.Member;
import com.hyeonah.hellojpa.domain.Order;
import com.hyeonah.hellojpa.domain.OrderItem;
import com.hyeonah.hellojpa.domain.OrderSearch;
import com.hyeonah.hellojpa.domain.item.Item;
import com.hyeonah.hellojpa.repository.ItemRepository;
import com.hyeonah.hellojpa.repository.MemberRepository;
import com.hyeonah.hellojpa.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hyeonahlee on 2020-11-17.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     * @param memberId
     * @param itemId
     * @param count
     * @return
     */
    @Transactional
    public Long order(final Long memberId, final Long itemId, final int count) {

        // 엔티티 조회
        final Member member = memberRepository.findOne(memberId);
        final Item item = itemRepository.findOne(itemId);

        // 배송 정보 생성
        final Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        final OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        final Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        // Cascade 범위? Order는 private owner 이고,
        // orderItem, delivery persist 다른 곳에서 참조하는 것이 없을 경우 (persist lifecycle) CascadeType.ALL를 사용한다.
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     *
     * @param orderId
     */
    @Transactional
    public void cancelOrder(final Long orderId) {
        // 주문 엔티티 조회
        final Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    /**
     * 주문 검색
     * @param orderSearch
     * @return
     */
    public List<Order> findOrders(final OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch);
    }
}
