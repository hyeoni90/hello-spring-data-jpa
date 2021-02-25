package com.hyeonah.hellojpa.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by hyeoni90 on 2021-02-25
 */
@Data
@AllArgsConstructor
public class Result<T> {

    private T data;
}
