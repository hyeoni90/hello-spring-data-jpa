package com.hyeonah.hellojpa.controller;

import com.hyeonah.hellojpa.domain.Address;
import com.hyeonah.hellojpa.domain.Member;
import com.hyeonah.hellojpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by hyeoni90 on 2021-02-14
 */
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/create")
    public String createMember(final Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/create")
    public String create(@Valid final MemberForm memberForm, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/createMemberForm";
        }

        final Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipCode());
        final Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("members")
    public String list(final Model model) {
        final List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
