package com.hyeonah.hellojpa.application.dto;

import lombok.Data;

/**
 * Created by hyeoni90 on 2021-02-24
 */
@Data
public class CreateMemberResponse {

    private Long id;

    public CreateMemberResponse(final Long id) {
        this.id = id;
    }
}
