package com.hyeonah.hellospringdatajpa.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * Created by hyeonahlee on 2020-11-13.
 */
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // JPA spec, 엔티티나, 임베디드 타입(Embeddable) 은 자바 기본 생성자를 public or protected로 설정
    protected Address() {
    }

    public Address(final String city, final String street, final String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
