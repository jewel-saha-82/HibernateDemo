package com.hibernate.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

	@Id
	@GeneratedValue
	private long id;
	@Column(length = 40, nullable = false)
	private String name;
	@Column(length = 40)
	private String city;
	private Integer age;
	@Column(length = 40)
	private String house;
	private LocalDate date;
	@Transient
	private String transit;
	@Column(precision = 5, scale = 2)
	private BigDecimal tax;
	@Lob
	private Byte[] blob;
	@Lob
	private String clob; 
	
	
}
