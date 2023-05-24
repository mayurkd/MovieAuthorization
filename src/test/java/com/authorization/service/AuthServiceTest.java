package com.authorization.service;

import com.authorization.model.User;
import com.authorization.repository.UserRepository;
import jakarta.servlet.ServletException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthServiceTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init()
    {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authService).build();
    }
    List<User> userList = new ArrayList<User>();
    @Test
    public void generateTokenSuccess() throws Exception
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
        when(userService.loginUser(user.getLoginId(),user.getPassword())).thenReturn(true);

        String token = authService.generateToken(user.getLoginId(),user.getPassword());
        assertTrue(token!=null);

    }
//    @Test
//    public void generateTokenFailure() throws ServletException, Exception
//    {
//        	User user = new User();
//        	user.setLoginId("1234");
//			user.setFirstName("Aditya");
//			user.setLastName("Allam");
//			user.setEmail("allamaditya99@gmail.com");
//			user.setPassword("Aditya@123");
// 			user.setConfirmPassword("Aditya@123");
//			user.setContactNumber("8250770493");
//
//
//        userList.add(user);
//        when(userService.loginUser(user.getLoginId(),user.getPassword())).thenReturn(false).then(authService.generateToken("","")).thenThrow(ServletException.class);
//        assertEquals(authService.generateToken("",""),new ServletException().getMessage());
//    }
}
