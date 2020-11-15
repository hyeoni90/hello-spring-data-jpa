package com.hyeonah.hellojpa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.hyeonah.hellojpa.domain.Member;
import com.hyeonah.hellojpa.repository.MemberRepository;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hyeonahlee on 2020-11-15.
 */
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @DisplayName("회원 가입 테스트")
    @Test
    void register() {
        // given
        Member member = new Member();
        member.setName("lee");

        // when
        Long saveId = memberService.join(member);

        // then
//        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @DisplayName("중복 회원 예외")
    @Test
    void duplicateRegister() {
        // given
        Member member = new Member();
        member.setName("lee2");

        Member member2 = new Member();
        member2.setName("lee2");

        // when
        memberService.join(member);
        try {
            memberService.join(member2); // 예외 발생!
        } catch (IllegalStateException e) {
            return;
        }

        // then
        fail("예외가 발생해야 함!");
    }

}
