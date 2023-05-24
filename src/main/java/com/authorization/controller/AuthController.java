package com.authorization.controller;

import java.util.HashMap;
import java.util.Map;

import com.authorization.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.model.User;
import com.authorization.repository.UserRepository;
import com.authorization.service.AuthService;
import com.authorization.service.UserService;
@RestController
@RequestMapping("auth/v1")
public class AuthController {
private Map<String, String> mapObj = new HashMap<String, String>();
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	
	@PostMapping("/addUser")
	public ResponseEntity<?> registerUser(@RequestBody User user)
	{
		if(userService.addUser(user)!=null)
	{
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
		return new ResponseEntity<String>("user registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@PatchMapping("/forgetPassword/{userId}")
	public ResponseEntity<?> passwordReset(@PathVariable("userId") String userId, @RequestBody User user)
	{
		Object passwordReset = userService.passwordReset(userId, user);
		User user2 = userRepository.findById(userId).get();
		
		if(passwordReset!=null && user2.getAnswer().equals(user.getAnswer())&& user.getPassword().equals(user.getConfirmPassword()))
	{
			String msg=String.format("Password Reset Successfully, Your current password : %s %n Use This Password for Login",user.getPassword());
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
	}else if(passwordReset!=null && user2.getAnswer()!=user.getAnswer()){
		String msg=String.format("Password Reset Failed,Your answer does not match with database data %n Please Enter Correct Answer");
		return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}else {
		return new ResponseEntity<String>("No User Found with userId :"+userId, HttpStatus.NO_CONTENT);
	}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> logiUser(@RequestBody User user)
	{
	
		try
		{
			String jwtToken = authService.generateToken(user.getLoginId(), user.getPassword());
			mapObj.put("Message", "User successfully logged in");
			mapObj.put("Token:", jwtToken);
			
		}
		catch(Exception e)
		{
			mapObj.put("Message", "User not logged in");
			mapObj.put("Token:", null);
			return new ResponseEntity<>(mapObj, HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<>(mapObj, HttpStatus.OK);
	}
	
}
