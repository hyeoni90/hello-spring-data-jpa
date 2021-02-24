package com.hyeonah.hellojpa.service;

import com.hyeonah.hellojpa.domain.Member;
import com.hyeonah.hellojpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hyeonahlee on 2020-11-15.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     *
     * @param member
     * @return
     */
    @Transactional
    public Long join(final Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(final Member member) {
        final List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(final Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(final Long id, final String name) {
        // id를 가지고 영속성 컨텍스트에서 찾아서 없다면, 디비에서 조회 후 영속성 컨텍스트에 member 올린 것을 반환
        final Member member = memberRepository.findOne(id);
        // 영속 상태의 member 데이터 변경 >> **변경 감지**
        // 트랜잭션 어노테이션에 의해서 트랜잭션이 끝날 때 spring aop 가 동작하면서 트랜잭션 관련 aop 끝나는 시점에 Transaction commit, JPA flush & commit
        member.setName(name);
    }
}
