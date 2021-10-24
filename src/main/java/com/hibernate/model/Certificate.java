package com.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Certificate {

    @Column(length = 50)
    private String course;
    @Column(length = 50)
    private String duration;
}
