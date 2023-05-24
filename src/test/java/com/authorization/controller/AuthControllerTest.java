package com.authorization.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.authorization.repository.UserRepository;
import com.authorization.service.AuthService;
import com.authorization.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.authorization.model.User;
import com.authorization.service.UserServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTest {
	@Mock
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private AuthService authService;

	@InjectMocks
	private AuthController userC;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userC).build();
	}

	List<User> userList = new ArrayList<User>();

	@Test
	public void addUserSuccess() throws Exception {
		User user = new User();
		user.setLoginId("1234");
		user.setFirstName("Aditya");
		user.setLastName("Allam");
		user.setEmail("allamaditya99@gmail.com");
		user.setPassword("Aditya@123");
		user.setConfirmPassword("Aditya@123");
		user.setContactNumber("8250770493");

		userList.add(user);
		when(userService.addUser(any())).thenReturn(user);

		assertEquals(1, userList.size());
		mockMvc.perform(MockMvcRequestBuilders.post("/auth/v1/addUser").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user)))
				.andExpect(MockMvcResultMatchers.status().isCreated());

	}

	@Test
	public void addUserFailure() throws Exception {

		when(userService.addUser(any())).thenReturn(null);

		User u1 = userService.addUser(null);
		assertNull(u1);

		mockMvc.perform(MockMvcRequestBuilders.post("/auth/v1/addUser").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(u1)))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}
	

	@Test
	public void logiUser() throws Exception {
		User user = new User();
		user.setLoginId("1234");
		user.setFirstName("Aditya");
		user.setLastName("Allam");
		user.setEmail("allamaditya99@gmail.com");
		user.setPassword("Aditya@123");
		user.setConfirmPassword("Aditya@123");
		user.setContactNumber("8250770493");
		userList.add(user);
		when(userService.loginUser(user.getLoginId(), user.getPassword())).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/auth/v1/login").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user)))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}
	
	@Test
	public void logiUserFailed() throws Exception {
		User user = new User();
		user.setLoginId("1234");
		user.setFirstName("Aditya");
		user.setLastName("Allam");
		user.setEmail("allamaditya99@gmail.com");
		user.setPassword("Aditya@123");
		user.setConfirmPassword("Aditya@123");
		user.setContactNumber("8250770493");
		userList.add(user);
		userList.remove(0);
		when(userService.loginUser(user.getLoginId(), user.getPassword())).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.post("/auth/v1/login").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(userList)))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}

}
