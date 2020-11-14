package com.hyeonah.hellospringdatajpa.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hyeonahlee on 2020-11-13.
 */
@Entity
@DiscriminatorValue("A")
@Getter
@Setter
public class Album extends Item {

    private String artist;
    private String etc;
}
