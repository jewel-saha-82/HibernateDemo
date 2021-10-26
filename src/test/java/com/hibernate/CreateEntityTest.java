package com.hibernate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.hibernate.model.Customer;
import com.hibernate.model.Product;
import com.hibernate.model.Student;

@TestMethodOrder(OrderAnnotation.class)
public class CreateEntityTest {

	private static SessionFactory factory;
	private Session session;

	@BeforeAll
	static void startSessionFactory() {
		factory = new Configuration().configure().buildSessionFactory();
	}

	@AfterAll
	static void closeSessionFactory() {
		factory.close();
	}

	@BeforeEach
	void openSession() {
		session = factory.openSession();
	}

	@AfterEach
	void closeSession() {
		session.close();
	}

	@Disabled
	@Test
	@Order(1)
	void insertStudent() {

		Transaction tx = session.beginTransaction();

		Student student = Student.builder().name("Jewel").age(3933333).city("Kolkata").build();
		session.save(student);

		tx.commit();

		BDDAssertions.then(student.getId()).isGreaterThan(0);
	}

	@Disabled
	@Test
	@Order(3)
	void getStudent() {

		long id = 1L;

		Student student = session.find(Student.class, id);

		BDDAssertions.then(student.getId()).isEqualTo(id);
	}

	@Disabled
	@Test
	@Order(2)
	void doesStudentExist() {

		long id = 10000000L;

		Student student = session.find(Student.class, id);

		BDDAssertions.then(student).isNull();
	}

	@Disabled
	@Test
	@Order(4)
	void listStudents() {

		Query<Student> query = session.createQuery("from Student", Student.class);
		List<Student> resultList = query.getResultList();

		BDDAssertions.then(resultList.size()).isNotEqualTo(0);
	}

	@Disabled
	@Test
	@Order(6)
	void update() {

		Transaction tx = session.beginTransaction();

		session.update(Student.builder().id(2L).age(101).city("Kolkata").name("Jewel").house("Gouri Kunja").build());

		tx.commit();

		Student student = session.find(Student.class, 1L);

		BDDAssertions.then(student.getAge()).isEqualTo(101);

	}

	@Disabled
	@Test
	@Order(5)
	void deleteStudent() {

		Query<Student> query = session.createQuery("from Student", Student.class);
		List<Student> resultList = query.getResultList();

		Student student = resultList.get(0);

		consumeTx(session::delete, student);

		Student deletedStudent = session.find(Student.class, student.getId());

		BDDAssertions.then(deletedStudent).isNull();
	}

	<T> void consumeTx(Consumer<Student> consumer, Student student) {
		Transaction tx = session.beginTransaction();
		consumer.accept(student);
		tx.commit();
	}

	@Disabled
	@Test
	void getVsLoad() {

		Student student = session.get(Student.class, 5L);
		BDDAssertions.then(student).isNotNull();
		session.get(Student.class, 5L);
		session.get(Student.class, 5L);
		session.get(Student.class, 5L);
		
		Assertions.assertThatThrownBy(() -> {
			Student s1 = session.load(Student.class, 1000L);
			s1.getAge();
		}).isInstanceOf(ObjectNotFoundException.class);
	}

	@Disabled
	@Test
	void manyToManyTest() {
		Transaction tx = session.beginTransaction();

		Customer c1 = Customer.builder().name("A").build();
		Customer c2 = Customer.builder().name("B").build();
		Customer c3 = Customer.builder().name("C").build();

		Product p1 = Product.builder().name("W").build();
		p1.setCustomers(Arrays.asList(c1, c2, c3));

		session.save(c1);
		session.save(c2);
		session.save(c3);

		session.save(p1);

		System.out.println("P1 ID = " + p1.getId());

		session.delete(p1);

		session.save(p1);

		System.out.println("P1 ID = " + p1.getId());

		tx.commit();

		
	}

	//@Disabled
	@Test
	void lazyEgarTest() {
		Product p5 = session.get(Product.class, 1);
		System.out.println("P5 ID = " + p5.getId());
		//System.out.println("Cusomers = " + p5.getCustomers());
	}

}
