package com.hyeonah.hellojpa.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by hyeoni90 on 2021-02-24
 */
@Data
@AllArgsConstructor
public class UpdateMemberResponse {

    private Long id;
    private String name;
}
