package com.hibernate.test;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class HibernateConnectionTest {

	@PostConstruct
	public void init() {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		System.out.println(factory);
	}
}
