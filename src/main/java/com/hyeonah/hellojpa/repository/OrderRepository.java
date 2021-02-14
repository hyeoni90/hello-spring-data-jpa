package com.hyeonah.hellojpa.repository;

import com.hyeonah.hellojpa.domain.Order;
import com.hyeonah.hellojpa.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyeonahlee on 2020-11-15.
 */
@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(final Order order) {
        em.persist(order);
    }

    public Order findOne(final Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAllByString(final OrderSearch orderSearch) {
        String jqpl = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jqpl = " where";
                isFirstCondition = false;
            } else {
                jqpl += " and";
            }
            jqpl += " o.status = :status";
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jqpl = " where";
                isFirstCondition = false;
            } else {
                jqpl += " and";
            }
            jqpl += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jqpl, Order.class)
            .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }

    /**
     * JPA Criteria (jqpl을 java code로 작성하여 동적쿼리 빌드 해주는 JPA 표준)
     * 하지만, 실무에서 안씀!
     *
     * @param orderSearch
     * @return
     */
    public List<Order> findAllByCriteria(final OrderSearch orderSearch) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        final Root<Order> o = cq.from(Order.class);
        final Join<Object, Object> m = o.join("member", JoinType.INNER);

        final List<Predicate> criteria = new ArrayList<>();

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            final Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            final Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        final TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }
}
