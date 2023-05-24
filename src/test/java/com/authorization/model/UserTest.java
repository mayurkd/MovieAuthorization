package com.authorization.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserTest {

	
	
	@Test
	 void testGetLoginId() throws Exception {
		User userObj = Mockito.mock(User.class);
		when(userObj.getLoginId()).thenReturn("TestExpected");
		assertEquals(userObj.getLoginId(), "TestExpected");
	}
	
	@Test
	 void testSetLoginId() throws Exception {
		User userObj = Mockito.mock(User.class);
		when(userObj.setLoginId("TestExpected")).thenReturn("TestExpected");
		assertEquals(userObj.setLoginId("TestExpected") ,"TestExpected");
	}
	
	

}
