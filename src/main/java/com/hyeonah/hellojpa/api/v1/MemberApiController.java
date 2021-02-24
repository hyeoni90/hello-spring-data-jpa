package com.hyeonah.hellojpa.api.v1;

import com.hyeonah.hellojpa.application.dto.CreateMemberRequest;
import com.hyeonah.hellojpa.application.dto.CreateMemberResponse;
import com.hyeonah.hellojpa.domain.Member;
import com.hyeonah.hellojpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by hyeoni90 on 2021-02-24
 */
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid final Member member) {
        final Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid final CreateMemberRequest request) {
        final Member member = new Member();
        member.setName(request.getName());

        final Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
}
