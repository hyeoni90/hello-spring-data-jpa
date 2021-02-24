package com.hyeonah.hellojpa.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * Created by hyeoni90 on 2021-02-24
 */
@Getter
@NoArgsConstructor
public class CreateMemberRequest {

    @NotEmpty
    private String name;
    
}
