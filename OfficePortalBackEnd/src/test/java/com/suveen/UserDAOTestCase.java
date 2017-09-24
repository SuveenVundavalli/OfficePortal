package com.suveen;

import static org.junit.Assert.*;

import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.suveen.dao.UserDAO;
import com.suveen.model.User;

public class UserDAOTestCase {
	
	@Autowired static AnnotationConfigApplicationContext context;
	@Autowired static User user;
	@Autowired static UserDAO userDAO;
	
	@BeforeClass
	public static void initialize() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.suveen");
		context.refresh();
		
		userDAO = (UserDAO) context.getBean("userDAO");
		user = (User) context.getBean("user");
				
	}

	//@Test
	public void saveUserTestCase() {
		user.setUserName("Suveen");
		user.setEmailId("suveenkumar.vundavalli@gmail.com");
		user.setPassword("Suveen");
		user.setFirstName("Suveen Kumar");
		user.setLastName("Vundavalli");
		user.setMobile("8686242020");
		user.setRole("ROLE_ADMIN");
		
		boolean flag = userDAO.save(user);
		assertEquals("saveUserTestCase", true, flag);
	}
	
	//@Test
	public void listUserTestCase() {
		int count = userDAO.list().size();
		assertEquals("listUserTestCase", 2,count);
	}
	
	@Test
	public void validateUserTestCase() {
		User user = userDAO.validate("Suveen", "Suveen");
		assertEquals("validateUserTestCase", 1, user.getUserId());
	}

}
