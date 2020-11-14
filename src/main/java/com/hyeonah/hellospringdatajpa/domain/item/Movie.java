package com.hyeonah.hellospringdatajpa.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hyeonahlee on 2020-11-13.
 */
@Entity
@DiscriminatorValue("M")
@Getter
@Setter
public class Movie extends Item {

    private String director;
    private String actor;
}
