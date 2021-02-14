package com.hyeonah.hellojpa.controller;

import com.hyeonah.hellojpa.domain.Member;
import com.hyeonah.hellojpa.domain.Order;
import com.hyeonah.hellojpa.domain.OrderSearch;
import com.hyeonah.hellojpa.domain.item.Item;
import com.hyeonah.hellojpa.service.ItemService;
import com.hyeonah.hellojpa.service.MemberService;
import com.hyeonah.hellojpa.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hyeoni90 on 2021-02-14
 */
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createOrderForm(final Model model) {
        final List<Member> members = memberService.findMembers();
        final List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") final Long memberId,
                        @RequestParam("itemId") final Long itemId,
                        @RequestParam("count") final int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") final OrderSearch orderSearch,
                            final Model model) {
        final List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable final Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
