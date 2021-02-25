package com.hyeonah.hellojpa.api.v2;

import com.hyeonah.hellojpa.application.dto.*;
import com.hyeonah.hellojpa.domain.Member;
import com.hyeonah.hellojpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hyeoni90 on 2021-02-24
 */
@RestController
@RequiredArgsConstructor
public class MemberV2ApiController {

    private final MemberService memberService;

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid final CreateMemberRequest request) {
        final Member member = new Member();
        member.setName(request.getName());

        final Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") final Long id,
                                               @RequestBody @Valid final UpdateMemberRequest request) {
        // command와 query 구분
        // update 후 반환하지 않고 새롭게 조회 한다. (유지 보수 편리성 증가함)
        memberService.update(id, request.getName());
        final Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        final List<Member> findMembers = memberService.findMembers();
        final List<MemberDto> members = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(members);
    }
}
