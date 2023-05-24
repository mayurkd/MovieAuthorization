package com.authorization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.authorization.model.User;
import com.authorization.repository.UserRepository;

@AutoConfigureMockMvc
@SpringBootTest
public class UserServiceImplementationTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserServiceImplementation userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
	}
	List<User> userList = new ArrayList<User>();
	
	@Test
	public void getAllUsersSuccess() throws Exception
	{
		User user = new User();
		user.setLoginId("1234");
		user.setFirstName("Aditya");
		user.setLastName("Allam");
		user.setEmail("allamaditya99@gmail.com");
		user.setPassword("Aditya@123");
		user.setConfirmPassword("Aditya@123");
		user.setContactNumber("8250770493");
		
		userList.add(user);
		when(userRepository.findAll()).thenReturn(userList);
		
		List<User> uList = userService.getAllUsers();
		assertEquals(userList, uList);
		
	}

}
