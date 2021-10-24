package com.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    
    @Id
    private int id;
    @Column(length = 50)
    private String department;

    @OneToOne(mappedBy = "department")
    @JoinColumn(name = "s_id")
    private Student student;


}
